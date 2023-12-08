# Author: almercog@gmail.com
@loan-business
Feature: Loan Service
  In order to simulate loan
  As a developer
  I want to make sure Simulate operations through REST API works fine

  Background: 
    Given I set http headers to
      | Accept       | application/json |
      | Content-Type | application/json |

  Scenario Outline: simulate loan with mandatory fields
    When A loan <request>
    And I send POST request to "/api/loan"
    Then I get <response>

    Examples: 
      | request                                                                                                                                                       | response                                                                                    |
      | "{\"payload\":{\"amount\":6000,\"rate\":\"5\",\"termType\":\"A\",\"repaymentTerm\":3,\"disbursementDate\":\"2023-10-03\",\"email\":\"almercog@gmail.com\"}}"  | "{\"code\":200,\"type\":\"OK\",\"message\":\"We sent you an email to almercog@gmail.com\"}" |
      | "{\"payload\":{\"amount\":8000,\"rate\":\"5\",\"termType\":\"A\",\"repaymentTerm\":10,\"disbursementDate\":\"2023-10-03\",\"email\":\"almercog@gmail.com\"}}" | "{\"code\":200,\"type\":\"OK\",\"message\":\"We sent you an email to almercog@gmail.com\"}" |

  Scenario Outline: simulate loan with all fields
    When A loan <request>
    And I send POST request to "/api/loan"
    Then I get <response>

    Examples: 
      | request                                                                                                                                                                                                        | response                                                                                    |
      | "{\"payload\":{\"amount\":6000,\"rate\":\"5\",\"termType\":\"M\",\"repaymentTerm\":120,\"withGracePeriod\":\"S\",\"gracePeriod\":\"3\",\"disbursementDate\":\"2023-10-03\",\"email\":\"almercog@gmail.com\"}}" | "{\"code\":200,\"type\":\"OK\",\"message\":\"We sent you an email to almercog@gmail.com\"}" |
      | "{\"payload\":{\"amount\":8000,\"rate\":\"5\",\"termType\":\"M\",\"repaymentTerm\":240,\"withGracePeriod\":\"S\",\"gracePeriod\":\"4\",\"disbursementDate\":\"2023-10-03\",\"email\":\"almercog@gmail.com\"}}" | "{\"code\":200,\"type\":\"OK\",\"message\":\"We sent you an email to almercog@gmail.com\"}" |
      