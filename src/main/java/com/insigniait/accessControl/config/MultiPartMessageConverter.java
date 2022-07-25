package com.insigniait.accessControl.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.net.URL;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.fileupload.MultipartStream;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation that allows converting multipart/form-data to DTO
 */
public class MultiPartMessageConverter implements HttpMessageConverter<Object> {

	private final FormHttpMessageConverter wrappedConverter = new AllEncompassingFormHttpMessageConverter();
	private final ObjectMapper objectMapper;

	public MultiPartMessageConverter(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		if (mediaType != null) {
			return MediaType.MULTIPART_FORM_DATA.includes(mediaType);
		} else {
			return true;
		}
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return this.wrappedConverter.canWrite(clazz, mediaType);
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return this.wrappedConverter.getSupportedMediaTypes();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object read(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		MediaType mediaType = inputMessage.getHeaders().getContentType();
		String boundary = mediaType.getParameters().get("boundary");
		if ((boundary == null) || (boundary.equals(""))) {
			return null;
		}
		final MultipartStream multipartStream = new MultipartStream(inputMessage.getBody(),
				boundary.getBytes(),
				1024,
				null);

		MultiValueMap<String, Object> map = parsContent(multipartStream, clazz);
		Constructor constructor = clazz.getConstructors()[0];
		Parameter[] parameters = constructor.getParameters();
		Object[] args = new Object[parameters.length];
		int index = 0;
		for (Parameter parameter : parameters) {
			JsonProperty jsonProperty = parameter.getAnnotation(JsonProperty.class);
			final List<Object> values = (jsonProperty != null) ? map.get(jsonProperty.value()) : map.get(parameter.getName());
			if (List.class.isAssignableFrom(parameter.getType())) {
				args[index] = values;
			} else if (values != null && !values.isEmpty()) {
				args[index] = values.get(0);
			}
			index++;
		}
		try {
			Object obj = constructor.newInstance(args);
			return obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private MultiValueMap<String, Object> parsContent(MultipartStream multipartStream, Class<?> clazz) throws IOException {
		final MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		Map<String, Field> fields = Arrays.stream(clazz.getFields()).collect(Collectors.toMap(Field::getName, Function.identity()));
		boolean nextPart = multipartStream.skipPreamble();
		while (nextPart) {
			Map<String, String> headers = Arrays.stream(multipartStream.readHeaders().split("\r\n")).map(row -> {
				String[] item = row.split(":");
				return new AbstractMap.SimpleEntry<String, String>(item[0], item.length > 1 ? item[1] : null);
			}).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			ContentDisposition contentDisposition = ContentDisposition.parse(headers.get("Content-Disposition"));
			String contentType = headers.get("Content-Type");
			MediaType mediaTypePart = StringUtils.hasLength(contentType) ? MediaType.parseMediaType(contentType) : null;
			ByteArrayOutputStream bodyStream = new ByteArrayOutputStream();
			multipartStream.readBodyData(bodyStream);
			mediaTypePart.getParameters();
			if (MediaType.TEXT_PLAIN.getType().equals(mediaTypePart.getType()) &&
					MediaType.TEXT_PLAIN.getSubtype().equals(mediaTypePart.getSubtype())) {
				map.add(contentDisposition.getName(), bodyStream.toString());
			} else if (MediaType.APPLICATION_OCTET_STREAM.equals(mediaTypePart)) {
				map.add(contentDisposition.getName(), new ByteResource(contentDisposition.getFilename(), bodyStream.toByteArray()));
			} else if (MediaType.APPLICATION_JSON.equals(mediaTypePart)) {
				Field field = fields.get(contentDisposition.getName());
				if (field != null) {
					if (List.class.isAssignableFrom(field.getType())) {
						final Class genericClass = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
						map.add(field.getName(), objectMapper.readValue(bodyStream.toByteArray(), genericClass));
					} else {
						map.add(field.getName(), objectMapper.readValue(bodyStream.toByteArray(), field.getType()));
					}
				}
			}
			bodyStream.close();

			nextPart = multipartStream.readBoundary();
		}
		return map;
	}

	class ByteResource implements Resource {

		private final String fileName;
		private final byte[] data;

		public ByteResource(String fileName, byte[] data) {
			this.fileName = fileName;
			this.data = data;
		}

		@Override
		public boolean exists() {
			return false;
		}

		@Override
		public URL getURL() throws IOException {
			return null;
		}

		@Override
		public URI getURI() throws IOException {
			return null;
		}

		@Override
		public File getFile() throws IOException {
			return null;
		}

		@Override
		public long contentLength() throws IOException {
			return data.length;
		}

		@Override
		public long lastModified() throws IOException {
			return 0;
		}

		@Override
		public Resource createRelative(String relativePath) throws IOException {
			return null;
		}

		@Override
		public String getFilename() {
			return fileName;
		}

		@Override
		public String getDescription() {
			return fileName;
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(data);
		}
	}

	@Override
	public void write(Object o, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
	}
}