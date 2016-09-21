/*
 * 
 * Copyright (c) 2016 1&1 Internet SE.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 *        
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.oneandone.relesia.test.webservice;

import java.io.IOException;
import java.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.oneandone.relesia.database.model.entity.UserEntity;


public class UserRestInterfaceIT {

	private final String TEST_ADMIN_USER = "testuser";
	private final String TEST_ADMIN_PWD = "testpwd";
	private final String RESOURCE_PATH = "users";
	private final String NEW_OBJECT_JSON = "{\"firstName\": \"TEST\","
			+ "\"lastName\": \"USERFORTEST\","
			+ "\"lastModified\": 1471439248000,"
			+ "\"email\": \"some@email.com\","
			+ "\"password\": \"encrypted password\"}";

	private String getFullResourcePath() {
		return RestTestUtil.getBaseURL() + "/" + RESOURCE_PATH;
	}

	@Test
	public void requestNewObjectCreationNoAuth_thenCreationIsRejected() throws ClientProtocolException, IOException {
		// Given
		HttpPost postRequest = new HttpPost(getFullResourcePath());

		postRequest.addHeader("Content-Type", "application/json");
		postRequest.addHeader("Accept", "application/json");
		postRequest.setEntity(new StringEntity(NEW_OBJECT_JSON));

		HttpResponse response = HttpClientBuilder.create().build().execute(postRequest);

		Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_UNAUTHORIZED);
	}
	
	@Test
	public void requestNewObjectCreation_thenCreationIsSuccesfull() throws ClientProtocolException, IOException {
		// Given
		HttpPost postRequest = new HttpPost(getFullResourcePath());

		postRequest.addHeader("Content-Type", "application/json");
		postRequest.addHeader("Accept", "application/json");
		postRequest.setEntity(new StringEntity(NEW_OBJECT_JSON));
		
		String encoding = Base64.getEncoder().encodeToString(String.join(":", TEST_ADMIN_USER, TEST_ADMIN_PWD).getBytes("utf-8"));
		postRequest.setHeader("Authorization", "Basic " + encoding);
		// When

		HttpClient client = HttpClientBuilder.create().build();

		HttpResponse response = client.execute(postRequest);

		//Then
		UserEntity resource = RestTestUtil.retrieveResourceFromResponse(response,
				UserEntity.class);
		
		Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_CREATED);
		Assert.assertNotNull(resource);
		Assert.assertNotNull(resource.getId());
		RestTestUtil.saveWorkId("" + resource.getId());		
	}

	@Test(dependsOnMethods = { "requestNewObjectCreation_thenCreationIsSuccesfull" })
	public void requestWithNoAcceptHeader_thenDefaultResponseContentTypeIsJson()
			throws ClientProtocolException, IOException {
		// Given
		String jsonMimeType = "application/json";
		HttpUriRequest request = new HttpGet(getFullResourcePath() + "/" + RestTestUtil.getWorkId());

		// When
		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		// Then
		String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
		Assert.assertEquals(mimeType, jsonMimeType);
	}

	@Test(dependsOnMethods = { "requestNewObjectCreation_thenCreationIsSuccesfull" })
	public void requestExistingId_thenRetrievedResourceIsCorrect() throws ClientProtocolException, IOException {
		// Given
		HttpUriRequest request = new HttpGet(getFullResourcePath() + "/" + RestTestUtil.getWorkId());

		// When
		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		// Then
		UserEntity resource = RestTestUtil.retrieveResourceFromResponse(response,
				UserEntity.class);
		Assert.assertNotNull(resource);
		Assert.assertEquals("" + resource.getId(), RestTestUtil.getWorkId());
	}

	@Test(dependsOnMethods = { "requestNewObjectCreation_thenCreationIsSuccesfull" })
	public void requestObjectUpdate_thenUpdateIsSuccesfull() throws ClientProtocolException, IOException {
		// Get existing object from DB
		HttpUriRequest getRequest = new HttpGet(getFullResourcePath() + "/" + RestTestUtil.getWorkId());

		HttpResponse response = HttpClientBuilder.create().build().execute(getRequest);

		UserEntity resource = RestTestUtil.retrieveResourceFromResponse(response,
				UserEntity.class);
		Assert.assertNotNull(resource);
		String newName = resource.getFirstName() + "_UPDATED_DURING_TEST";
		resource.setFirstName(newName);

		// Update with new value
		HttpPut putRequest = new HttpPut(getFullResourcePath());
		putRequest.addHeader("Content-Type", "application/json");
		putRequest.addHeader("Accept", "application/json");
		putRequest.setEntity(new StringEntity(RestTestUtil.getJSONFormat(resource)));
		
		String encoding = Base64.getEncoder().encodeToString(String.join(":", TEST_ADMIN_USER, TEST_ADMIN_PWD).getBytes("utf-8"));
		putRequest.setHeader("Authorization", "Basic " + encoding);
		
		HttpClient client = HttpClientBuilder.create().build();
		response = client.execute(putRequest);
		
		Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);

		// Get again the object and check updated value
		response = HttpClientBuilder.create().build().execute(getRequest);

		resource = RestTestUtil.retrieveResourceFromResponse(response, UserEntity.class);
		Assert.assertNotNull(resource);
		Assert.assertEquals(resource.getFirstName(), newName);
	}

	@Test(dependsOnMethods = { "requestObjectUpdate_thenUpdateIsSuccesfull" })
	public void requestExistingObjectDelete_thenResponseIsOK() throws ClientProtocolException, IOException {
		// Given
		HttpUriRequest request = new HttpDelete(getFullResourcePath() + "/" + RestTestUtil.getWorkId());

		// When
		String encoding = Base64.getEncoder().encodeToString(String.join(":", TEST_ADMIN_USER, TEST_ADMIN_PWD).getBytes("utf-8"));
		request.setHeader("Authorization", "Basic " + encoding);
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(request);

		// Then
		Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_NO_CONTENT);
	}

	@Test(dependsOnMethods = { "requestExistingObjectDelete_thenResponseIsOK" })
	public void requestInexistantID_then404IsReceived() throws ClientProtocolException, IOException {
		System.out.println("connecting to : " + getFullResourcePath() + "/" + RestTestUtil.getWorkId());
		// Given
		HttpUriRequest request = new HttpGet(getFullResourcePath() + "/" + RestTestUtil.getWorkId());

		// When
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

		// Then
		Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
	}
}
