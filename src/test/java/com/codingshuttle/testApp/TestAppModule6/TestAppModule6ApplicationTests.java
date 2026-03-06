package com.codingshuttle.testApp.TestAppModule6;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestAppModule6ApplicationTests {


	@BeforeEach
   void setup(){
	   System.out.println("I will be executed before each test Method");
   }

   @AfterEach
	void tearDown(){
		System.out.println("I will be executed after each test Method");
	}

	@BeforeAll
    static void setupInitiallyOnce(){
		System.out.println("I will be executed before all test Method Once(Initialise)");
	}

	@AfterAll
    static void tearDownLastOnce(){
		System.out.println("I will be executed after all test Method Once (ex:after use clean at last)");
	}

	@Test
	@DisplayName("Method:1")
	@Order(2)
	void contextLoads() {
	}

	@Test
	@Disabled
	void method2(){
		System.out.println("Disable this Method with the Annotation");
	}

	@Test
	@Order(1)
	void method3(){
		int a = 5;
		int b = 3;
		int result = add(a,b);
//		Assertions.assertEquals(8,result);
		assertThat(result).isEqualTo(8);
	}

	@Test
	void method4(){
		int a = 5;
		int b = 0;

		assertThatThrownBy(()->dividewithException(a,b))
				.isInstanceOf(ArithmeticException.class)
				.hasMessage("Tried to divide by 0") //even message should be equal thrown by exception
		;

	}

	int add(int a, int b){
		return a+b;
	}

	int dividewithException(int a, int b){
		try {
			return a/b;
		}catch (Exception ex){
			throw new ArithmeticException("Tried to divide by 0");
		}
	}

}
