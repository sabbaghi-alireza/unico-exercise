secureapp: Secure Rest(JAX-RS) and SOAP(JAX-WS) endpoints based on Java EE 7 Technologies Deployed as an EAR
==============================================================================================
Author: Alireza Sabbaghi
Level: Advanced
Technologies: Apache Shiro 1.4, Apache CXF, WS-Security, JBossWS, CDI 1.1, EJB 3.2, JPA 2.1, JMS 2.0 and Bean Validation 1.1
Target Project: WildFly 10

What is it?
-----------

This project has been done as a coding exercise for Unico company.

Problem Definition
-----------

Develop an enterprise Java application that implements RESTful and SOAP web services that is not able to be hacked.
 
The RESTful service will expose two methods:
 
public String push(int i1, int i2);
 
which returns the status of the request to the caller as a String. The two parameters will be added to a JMS queue.
 
public List<Integer> list();
 
which returns a list of all the elements ever added to the queue from a database in the order added as a JSON structure.
 
The SOAP service will expose the following method as operations:
 
public int gcd();
 
which returns the greatest common divisor* of the two integers at the head of the queue. These two elements will subsequently be discarded from the queue and the head replaced by the next two in line.
 
public List<Integer> gcdList();
 
which returns a list of all the computed greatest common divisors from a database.
 
public int gcdSum();
 
which returns the sum of all computed greatest common divisors from a database.

System requirements
-------------------

All you need to build this project is Java 8.0, Maven 3.1 or better.

The application this project produces is designed to be run on JBoss WildFly 10.

Start JBoss WildFly with the Full Profile (for using JMS)
-------------------------

1. Open a command line and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server with the full profile:

        For Linux:   JBOSS_HOME/bin/standalone.sh -c standalone-full.xml
        For Windows: JBOSS_HOME\bin\standalone.bat -c standalone-full.xml

 
Build and Deploy the Application
-------------------------

_NOTE: The following build command assumes you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line. See [Build and Deploy the Quickstarts](https://github.com/jboss-developer/jboss-eap-quickstarts#build-and-deploy-the-quickstarts) for complete instructions and additional options._

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this application.
3. Type this command to build and deploy the archive:

        mvn clean package wildfly:deploy

4. This will deploy `target/secureapp.ear` to the running instance of the server.


Access the application 
---------------------

The application will be running at the following URL: <http://localhost:8080/secureapp-web>.

1. The Rest Endpoint address: http://localhost:8080/secureapp-web/rest/elements

    1.1. for pushing data:      send POST with json object like {"first":10, "second":20} using http basic authentication (user1/pass1) to (user20/pass20)

    1.2. for view list of data: send GET using http basic authentication (user1/pass1) to (user20/pass20)

2. The Soap Endpoint address: http://localhost:8080/secureapp-web/soap/ElementSoapService
    
    2.1. This service is secured using WS-Security UsernameToken authentication: (user1/pass1) to (user20/pass20)

Undeploy the Archive
--------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this application.
3. When you are finished testing, type this command to undeploy the archive:

        mvn wildfly:undeploy


Run the Arquillian Tests 
-------------------------

This application provides Arquillian tests. By default, these tests are configured to be skipped as Arquillian tests require the use of a container. 

_NOTE: The following commands assume you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line. See [Run the Arquillian Tests](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/RUN_ARQUILLIAN_TESTS.md) for complete instructions and additional options._

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type the following command to run the test goal with the following profile activated:

        mvn clean test -Parq-wildfly-remote


Investigate the Console Output
---------------------
You should see the following console output when you run the tests:

    Results :
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

