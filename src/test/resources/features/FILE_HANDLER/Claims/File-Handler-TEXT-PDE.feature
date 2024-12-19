Feature: Claims PDE Text File Ingestion

  Scenario Outline: Validate PDE Text File against DB per Text Test File
    Given TestCaseDataSetup, File-"PDE-tests.xlsx", Sheet-"PDE", TestCase-"<TestCaseId>"
    Given the file "PDE-current.txt" is downloaded from the S3 bucket
    And the file "PDE-current.txt" follows the specification in "PDE-Specs"
    When the "Claims" microservice processes the file
    Then verify "PDE-current.txt" data is in the "PDE-Claims-DB" database
    Examples:
      | TestCaseId |
      | 1          |
      | 2          |
      | 3          |

  Scenario: Validate PDE Text File against DB per Data Table
    Given the file "PDE-current.xlsx" is downloaded from the S3 bucket
    And the file "PDE-current.xlsx" follows the specification in "PDE-Specs"
    When the "Claims" microservice processes the file
    Then verify "PDE-current.txt" data in the "PDE-Claims-DB" database matches values:
      | test_case_id | sql_description      | sql_query                 | expected_value         |
      | 1            | Retrieving Drug Name | select claim_id from PDE; | DET0000001, DET0000002 |
      | 2            | Count all rows       | select count(*) from PDE; | 32                     |

  Scenario: Validate PDE Text File against DB per Data Table using Dynamic Expected Values
    Given the file "PDE-current.xlsx" is downloaded in the S3 bucket identified by "IDR"
    And the file "PDE-current.xlsx" follows the specification in "PDE-Specs.json"
    When the "Claims" microservice processes the file
    Then verify "PDE-current.txt" data in the "MFP-Claims-DB" database matches values:
      | test_case_id | sql_description      | sql_query                 | expected_value          |
      | 1            | Retrieving Drug Name | select claim_id from PDE; | getFromFile("claim_id") |
      | 2            | Count all rows       | select count(*) from PDE; | getRowCountFromFile()   |