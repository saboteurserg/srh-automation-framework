SRH AutomationFramework
===============

Java based Web Automation Framework which has everything needed to start writing tests in few seconds.

This framework combines following libraries and frameworks in one. In order to efficiently use it It's recommended to get familiar with all of them:

* Java 8 (Selenium > 3.0 requires Java v.8)
* TestNG - http://testng.org/doc/index.html
* Selenium - http://docs.seleniumhq.org/
* Selenide - http://selenide.org/
* AssertJ + AssertJ-DB - http://joel-costigliola.github.io/assertj/ (it's included though you can use other assertion libraries, E.g. TestNG, Selenide etc.)

* (for reports used ExtentRepotrs - http://extentreports.relevantcodes.com/ )
* (for logging used Logback+SLF4J - http://logback.qos.ch/ - http://www.slf4j.org/ )

## [PLEASE ALSO READ WIKI !!!](wiki)

## Quick Start Guide

### 1. Add Maven Dependency

Current version is:

 [CHANGELOG could be found here](CHANGELOG)

```
    <dependencies>
        <dependency>
            <groupId>com.krynytskyyserhiy</groupId>
            <artifactId>srh-automation-framework</artifactId>
            <version>1.0</version>
        </dependency>
    </dependencies>
```



### 2. Add SRHTestngListener

#### If you're running directly from your IDE:

Just add listener in your TestNG running profile.

#### If you're running suite xml file (recommended):

```
    <listeners>
        <listener class-name="SRHTestngListener" />
        ...
    </listeners>
```

#### If you're running with maven (add in pom file):

```
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.5</version>
                <configuration>
                    <properties>
                       <property>
                            <name>listener</name>
                            <value>SRHTestngListener</value>
                        </property>
                    </properties>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

### All html reports, logs and screenshots by default will be saved in 'target' folder.