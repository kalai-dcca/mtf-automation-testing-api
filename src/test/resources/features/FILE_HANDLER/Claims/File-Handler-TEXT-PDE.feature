Feature: Claims PDE Text File Ingestion

  Scenario: Successfully ingest a valid PDE text file

    # Can be a health check if API is provided.
    Given the “claimsService” is running
    # When Launch "claims/health", Method: "GET"
    # Then Verify status code 200

    # Assuming we have an S3 to pull from. create api step to download from S3. Can be mock.
    Given download new S3 file from  "DDPS/PDE-current"
    # When Launch "DDPS/PDE-current", Method: "GET"
    # Then Verify status code 201
    # Then Download "PDE-current.txt" file

    # Implement this
    #Then the file is valid per "PDE-Specs.json"

    # Since we are not actually pulling from S3, we can pull from locally
    Then the "PDE-current.txt" is valid per "PDE-Specs.json"

    # Assuming we have an API to post to
    When the file is is posted to "claimsService"
    Then the file is successfuly ingested
    # When Launch "claims/upload", Method: "POST"
    # Then Verify status code 201

    # Don't have DB Model yet / how to access DB. Can Mock.
    # Each Microservice needs to define access
    # Each file to test e.g, MFP File, will have unique SQL queries to test
    Then the database is updated with the PDE Data