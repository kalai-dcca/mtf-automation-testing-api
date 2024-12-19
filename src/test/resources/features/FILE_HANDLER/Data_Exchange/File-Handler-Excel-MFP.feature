Feature: Data Exchange MFP Excel File Ingestion

  Scenario: Successfully ingest a valid MFP excel file into Database
    Given the “dataExchangeService” is running
    Given download new S3 file from  "IDR/MFP-current"
    Then the file is valid per "MFP-Specs.json"
    When the file is is posted to "dataExchangeService"
    Then the file is successfuly ingested
    Then the database is updated with the MFP Data

  Scenario: Successfully ingest a valid MFP excel file via API
    # health check
    When Launch "dataExchange/health", Method: "GET"
    Then Verify status code 200

    # Download and validate file
    When Launch "IDR/MFP-current", Method: "GET"
    Then Verify status code 201
    Then Download "MFP-current.xlsx" file
    Then the file is valid per "MFP-Specs.json"

    # Validate database
    Then the database is updated with the MFP Data


  Scenario: Successfully ingest a valid MFP excel file without API
    # make generic, regardless if S3 bucket or API call
    Given Download "MFP-current.xlsx" file
    When the file is valid per "MFP-Specs.json"
    Then the database is updated with the MFP Data