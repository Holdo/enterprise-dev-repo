Web repository for development artifacts
========================================

An implementation of a responsive web application which enables users to store, version and perform search on their development objects like WARs, WSDLs and XSDs. For more information please visit our:

* [ApiDoc](http://holdo.github.io/enterprise-dev-repo/apidocs/)
* [Webpage](http://holdo.github.io/enterprise-dev-repo/)
* [Wiki](https://github.com/Holdo/enterprise-dev-repo/wiki)

How to run
----------

1. `git clone https://github.com/Holdo/enterprise-dev-repo.git`

2. `cd enterprise-dev-repo`

3. `mvn clean install`

4. `cd webmvc`

5. `mvn spring-boot:run`

6. Open in web browser* [http://localhost:8080/](http://localhost:8080/)

7. You can try uploading some of our [test* files](https://github.com/Holdo/enterprise-dev-repo/tree/master/webmvc/src/test/resources)

\* at least Internet Explorer 11 because of WebSockets API (SockJS was not used)

Developers
----

* Michal Holič
* Ondřej Gasior
* Peter Hutta
