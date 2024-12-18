Feature: Data Exchange NCPDP Excel File Ingestion

  Scenario: Successfully ingest a valid NCPDP excel file

    # Can be a health check if API is provided.
    Given the “dataExchangeService” is running
    # When Launch "dataExchange/health", Method: "GET"
    # Then Verify status code 200

    # Assuming we have an S3 to pull from. create api step to download from S3. Can be mock.
    Given download new S3 file from  "IDR/NCPDP-current"
    # When Launch "IDR/NCPDP-current", Method: "GET"
    # Then Verify status code 201
    # Then Download "NCPDP-current.xlsx" file

    # Implement this
    Then the file is valid per "NCPDP-Specs.json"

    # Since we are not actually pulling from S3, we can pull from locally
    Then the "NCPDP-current.xlsx" is valid per "NCPDP-Specs.json"

    # Assuming we have an API to post to
    When the file is is posted to "dataExchangeService"
    Then the file is successfuly ingested
    # When Launch "dataExchange/upload", Method: "POST"
    # Then Verify status code 201

    # Don't have DB Model yet / how to access DB. Can Mock.
    # Each Microservice needs to define access
    # Each file to test e.g, NCPDP File, will have unique SQL queries to test
    Then the database is updated with the NCPDP Data