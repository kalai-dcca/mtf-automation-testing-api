Feature: Data Exchange MFP Excel File Ingestion

  Scenario: Successfully ingest a valid MFP excel file

    # Can be a health check if API is provided.
    Given the “dataExchangeService” is running
    # When Launch "dataExchange/health", Method: "GET"
    # Then Verify status code 200

    # Assuming we have an S3 to pull from. create api step to download from S3. Can be mock.
    Given download new S3 file from  "IDR/MFP-current"
    # When Launch "IDR/MFP-current", Method: "GET"
    # Then Verify status code 201
    # Then Download "MFP-current.xlsx" file

    # Implement this
    Then the file is valid per "MFP-Specs.json"

    # Since we are not actually pulling from S3, we can pull from locally
    Then the "MFP-current.xlsx" is valid per "MFP-Specs.json"

    # Assuming we have an API to post to
    When the file is is posted to "dataExchangeService"
    Then the file is successfuly ingested
    # When Launch "dataExchange/upload", Method: "POST"
    # Then Verify status code 201

    # Don't have DB Model yet / how to access DB. Can Mock.
    # Each Microservice needs to define access
    # Each file to test e.g, MFP File, will have unique SQL queries to test
    Then the database is updated with the MFP Data