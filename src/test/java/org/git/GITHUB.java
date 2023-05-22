package org.git;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.base.BaseClass;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class GITHUB extends BaseClass {

	@BeforeMethod
	public void beforeMethod() throws IOException {
		baseURI = getPropertyFileValue("baseURI");
	}

	@Test(priority = 0)
	public void getUser() {
		given().get("users/ItsUnicMadhan").then().assertThat().statusCode(200).body("id", isA(Integer.class)).log()
				.all();
	}

	@Test(priority = 1)
	public void getRepo() {
		given().get("users/ItsUnicMadhan/repos").then().assertThat().statusCode(200)
				.body("[0].name", equalTo("Methods")).log().all();
	}

	@Test(priority=2)
	public void create() throws IOException {
		given().header("Authorization", "Bearer " + getPropertyFileValue("token"))
				.header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(new File(getPropertyFileValue("jsonPathOfNewRepo"))).when().post("user/repos").then().assertThat()
				.statusCode(201).body("name", equalTo("REST")).log().all();
	}

	@Test(priority=3)
	public void update() throws IOException {
		given().header("Authorization", "Bearer " + getPropertyFileValue("token"))
				.header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(new File(getPropertyFileValue("jsonPathOfUpdateRepo"))).when()
				.patch("repos/ItsUnicMadhan/REST").then().assertThat().statusCode(200)
				.body("name", equalTo("RESTAssured")).log().all();
	}

	@Test(priority=4)
	public void delete() throws IOException {
		given().header("Authorization", "Bearer " + getPropertyFileValue("token")).when()
				.delete("repos/ItsUnicMadhan/RESTAssured").then().assertThat().statusCode(204).log().all();
	}

}
