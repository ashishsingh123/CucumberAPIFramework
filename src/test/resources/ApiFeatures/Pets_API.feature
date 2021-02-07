Feature: Pets API sample for API automation

  #Checking POST request
  Scenario Outline: View All Users in Safety Portal
    #Precondition
    Given I set api base url "<RequestURI>"
    And I generated bearer token number using token request URL "<AuthAPI>" and token number "<AuthAPIToken>"
    And I set header values
      | ContentType      |
      | application/json |
    And I set post body text from json file "filter"
    When I perform post request using URI path "<URIPath>"
    Then I verify response code <ResponseCode>
    And I verify response content type "application/json"

    # User data verification using UserModel
    Examples: 
      | AuthAPI                                             | AuthAPIToken                             | RequestURI                                           | URIPath                              | ResponseCode |
      | https://authapi-dev-offshore03.epharmasolutions.com | Q1JPMDEtZGV2b2Zmc2hvcmUwMzptNEcyUjZGRnRR | https://coreapi-dev-offshore03.epharmasolutions.com/ | /api/v1.0/StudySite/GetAllStudySites |          200 |

    #Checking GET request
  @PetsAPI
  Scenario Outline: RestAssured API Unit Test Get Pets by status
    Given I reset API variable data
    Given I set api base url "<ResquestUrl>"
    And I set header values
      | ContentType      |
      | application/json |
    And I set URI query string
      | status  |
      | pending |
    When I perform get request using URI path "<ResquestPath>"
    Then I verify response code <ResponseCode>
    And I verify response content type "application/json"

    Examples: 
      | ResquestUrl                  | ResquestPath        | ResponseCode |
      | https://petstore.swagger.io/ | v2/pet/findByStatus |          200 |

    #Checking POST request
  @PetsAPI
  Scenario Outline: RestAssured API unit test Post Pets using json body
    Given I reset API variable data
    Given I set api base url "<ResquestUrl>"
    And I set header values
      | ContentType      |
      | application/json |
    And I set post body text from json file "PetsData"
    When I perform post request using URI path "<ResquestPath>"
    Then I verify response code <ResponseCode>
    And I verify response content type "application/json"
    And I verify data in JSON reponse
      | NodePath | NodeName | Value     |
      |          | name     | doggie    |
      |          | status   | available |
      | category | name     | string    |

    Examples: 
      | ResquestUrl                  | ResquestPath | ResponseCode |
      | https://petstore.swagger.io/ | /v2/pet      |          200 |

 @PetsAPI
  Scenario Outline: RestAssured API unit test Post Pets using Param
    Given I reset API variable data
    Given I set api base url "<ResquestUrl>"
    And I set header values
      | ContentType                       | accept           |
      | application/x-www-form-urlencoded | application/json |
    And I set post from params
      | name    | status  |
      | testing | pending |
    When I perform post request using URI path "<ResquestPath>"
    Then I verify response code <ResponseCode>
    And I verify response content type "application/json"

    Examples: 
      | ResquestUrl                  | ResquestPath | ResponseCode |
      | https://petstore.swagger.io/ | /v2/pet/1    |          200 |

   #Checking PUT request
  @PetsAPI
  Scenario Outline: RestAssured API unit test Put pets using json body
    Given I reset API variable data
    Given I set api base url "<ResquestUrl>"
    And I set header values
      | ContentType      |
      | application/json |
    And I set post body text from json file "PetsData"
    When I perform put request using URI path "<ResquestPath>"
    Then I verify response code <ResponseCode>
    And I verify response content type "application/json"
    And I verify data in JSON reponse
      | NodePath | NodeName | Value     |
      |          | name     | doggie    |
      |          | status   | available |
      | category | name     | string    |

    Examples: 
      | ResquestUrl                  | ResquestPath | ResponseCode |
      | https://petstore.swagger.io/ | /v2/pet      |          200 |

    #Checking GET request
 @PetsAPI
  Scenario Outline: RestAssured API unit test Get pets using pets Id
    Given I reset API variable data
    Given I set api base url "<ResquestUrl>"
    And I set header values
      | ContentType      |
      | application/json |
    When I perform get request using URI path "<ResquestPath>"
    Then I verify response code <ResponseCode>
    And I verify response content type "application/json"

    Examples: 
      | ResquestUrl                  | ResquestPath | ResponseCode |
      | https://petstore.swagger.io/ | /v2/pet/1    |          200 |

   #Checking DELETE request
  @PetsAPI
  Scenario Outline: RestAssured API unit test delete pets using pets id
    Given I reset API variable data
    Given I set api base url "<ResquestUrl>"
    And I set header values
      | ContentType      |
      | application/json |
    When I perform delete request using URI path "<ResquestPath>"
    Then I verify response code <ResponseCode>
    And I verify response content type "application/json"

    Examples: 
      | ResquestUrl                  | ResquestPath | ResponseCode |
      | https://petstore.swagger.io/ | /v2/pet/1    |          200 |
