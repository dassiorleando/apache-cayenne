Apache-cayenne
=========================
Basic Apache Cayenne examples.

Prerequisites
-------------
- JDK 1.8+
- Maven installed (mvn)

Resources
---------
- [Documentation](https://cayenne.apache.org/docs/4.1/)
- [DB First Flow](https://cayenne.apache.org/docs/4.1/getting-started-db-first/)

Commands
--------
- ```mvn clean install```: Cleaning up the project
- ```mvn cayenne:cdbimport```: Synchronizes the map file with an existing DB
- ```mvn cayenne:cgen```: Generates model classes for our tables
