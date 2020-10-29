[![Master Build Status](https://img.shields.io/travis/IMSGlobal/caliper-java.svg?label=master)](https://travis-ci.org/IMSGlobal/caliper-java)
[![Develop Build Status](https://img.shields.io/travis/IMSGlobal/caliper-java/develop.svg?label=develop)](https://travis-ci.org/IMSGlobal/caliper-java/develop)

# IMS Global Learning Consortium, Inc.

# caliper-java
The [Caliper Analytics® Specification](https://www.imsglobal.org/caliper/v1p1/caliper-spec-v1p1) 
provides a structured approach to describing, collecting, and exchanging learning activity data at 
scale. Caliper also defines an application programming interface (the Sensor API™) for marshalling 
and transmitting event data from instrumented applications to target endpoints for storage, 
analysis, and use.

*caliper-java* is a reference implementation of the Sensor API™ written in Java.

## Branches
* __master__: stable, deploy-able branch that stores the official release history.  
* __develop__: unstable development branch. Current work that targets a future release is merged 
to this branch.

## Tags
*caliper-java* releases are tagged and versioned MAJOR.MINOR.PATCH\[-label\] (e.g., 1.1.1). 
Pre-release tags are identified with an extensions label (e.g., "1.2.0-RC01"). The tags are stored 
in this repository.

## Contributing
We welcome the posting of issues by non-IMS Global Learning Consortium members (e.g., feature 
requests, bug reports, questions, etc.), but we *do not* accept contributions in the form of pull 
requests from non-members. See [CONTRIBUTING.md](./CONTRIBUTING.md) for more 
information.

## Getting Started

### Prerequisites
*caliper-java* uses Apache [Maven](https://maven.apache.org/) 3.x to manage the build process, 
documentation, and reporting.

### Building

1) Clone the IMS Global *caliper-java* project repository to your development machine. 
    * We recommend forking the repository using your GitHub account and then cloning the fork.
        ```
        git clone https://github.com/{your GitHub id}/caliper-java.git  # HTTPS
        git clone git@github.com:{your GitHub id}/caliper-java.git      # SSH
        ```

2) Gain access to the JSON-LD fixtures in [*caliper-spec*](https://github.com/imsglobal/caliper-spec).
    * Clone *caliper-spec* to your development machine somewhere outside of your *caliper-java* repository. We 
      typically set up the two projects so they reside in sibling directories.
      ```
      git clone https://github.com/imsglobal/caliper-spec.git  # HTTPS
      git clone git@github.com:imsglobal/caliper-java.git      # SSH
      ```
    * Checkout the `develop` branch. The `fixtures` directory will then be accessible. (Note: the develop branch of 
      *caliper-spec* may be merged to the master branch in the near future).
        ```
        cd caliper-spec
        git checkout develop
        ```

3) Back in *caliper-java*, add symbolic links to the v1p1 and v1p2 fixture directories in *caliper-spec.*
    * From the root directory, create a `resources` directory under `src/test` and a `fixtures` directory inside 
      `resources`.
        ```
        mkdir src/test/resources
        mkdir src/test/resources/fixtures 
        ```
    * Then, create two symbolic links from the `v1p1` and `v1p2` directories under `fixtures` in *caliper-spec*. When 
      creating links from the project's root, the paths must be absolute or begin with the home (`~`) symbol.
        ```
        ln -s /absolute/path/to/caliper-spec/fixtures/v1p1 /absolute/path/to/caliper-java/src/test/resources/fixtures/v1p1
        ln -s /absolute/path/to/caliper-spec/fixtures/v1p2 /absolute/path/to/caliper-java/src/test/resources/fixtures/v1p2   
        ```

4) Run a Maven install command.
    
    * You can run the standard build process and tests with the following:
        ```
        ./mvnw clean install
        ```
    * You can also build a `.jar` file with all the dependencies compiled by invoking the `uber-jar` build profile. This
      will create a file at the path `target/caliper-java-{version}.jar`.
        ```
        mvn clean -P uber-jar install
        ```

### Dependency Management
You can specify *caliper-java* as a project or module dependency in the appropriate `pom.xml` file:

```
<dependency>
    <groupId>org.imsglobal.caliper</groupId>
    <artifactId>caliper-java</artifactId>
    <version>1.2.0</version>
</dependency>
```  

## License
This project is licensed under the terms of the GNU Lesser General Public License (LGPL), version 3. 
See the [LICENSE](./LICENSE) file for details. For additional information on licensing options for 
IMS members, please see the [NOTICE](./NOTICE.md) file.