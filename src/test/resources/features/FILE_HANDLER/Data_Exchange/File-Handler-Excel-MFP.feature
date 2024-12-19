Feature: Data Exchange MFP Excel File Ingestion

  Scenario Outline: Validate MFP Excel File against DB per Excel Test File
    Given TestCaseDataSetup, File-"MFP-tests.xlsx", Sheet-"MFP", TestCase-"<TestCaseId>"
    Given the file "MFP-current.xlsx" is downloaded from the S3 bucket
    And the file "MFP-current.xlsx" follows the specification in "MFP-Specs"
    When the "dataXchange" microservice processes the file
    Then verify "MFP-current.xlsx" data is in the "MFP-DataXchange-DB" database
    Examples:
      | TestCaseId |
      | 1          |
      | 2          |
      | 3          |

  Scenario: Validate MFP Excel File against DB per Data Table
    Given the file "MFP-current.xlsx" is downloaded from the S3 bucket
    And the file "MFP-current.xlsx" follows the specification in "MFP-Specs"
    When the "dataXchange" microservice processes the file
    Then verify "MFP-current.xlsx" data in the "MFP-DataXchange-DB" database matches values:
      | test_case_id | sql_description      | sql_query                   | expected_value            |
      | 1            | Retrieving Drug Name | select ndc_eleven from mfp; | insulin, tylenol, vaccine |
      | 2            | Count all rows       | select count(*) from mfp;   | 32                        |

  Scenario: Validate MFP Excel File against DB per Data Table using Dynamic Expected Values
    Given the file "MFP-current.xlsx" is downloaded in the S3 bucket identified by "IDR"
    And the file "MFP-current.xlsx" follows the specification in "MFP-Specs.json"
    When the "dataXchange" microservice processes the file
    Then verify "MFP-current.xlsx" data in the "MFP-DataXchange-DB" database matches values:
      | test_case_id | sql_description      | sql_query                   | expected_value            |
      | 1            | Retrieving Drug Name | select ndc_eleven from mfp; | getFromFile("ndc_eleven") |
      | 2            | Count all rows       | select count(*) from mfp;   | getRowCountFromFile()     |