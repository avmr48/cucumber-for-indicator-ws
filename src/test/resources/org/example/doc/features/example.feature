Feature: Get indicators

  In order to know things
  A manager needs to get indicators

  # Business scenario
  Scenario: Get list of indicators

    When I ask for indicators:
      | ids      | place       | time            | description | with related | with components |
      | ---      | -----       | ----            | ----------- | ------------ | --------------- |
      | i_1, i_2 | FR-IDF-0001 | 201801 - 201901 | Y           | Y            | N               |

    Then I should get list of indicators:
      | id  | name         | description                      |
      | --  | ----         | -----------                      |
      | i_1 | Indicator 01 | The description for Indicator 01 |
      | i_2 | Indicator 02 | The description for Indicator 02 |

    # ==================================================================================================================

    # main version
    # 1 table for every indicators

    And I should get indicator values:
      | id    | time   | place       | value | goal  |
      | ----- | -----  | -----       | ----- | ----- |
      | i_1   | 201901 | FR-IDF-0001 | 1.2   | 1.55  |
      | i_1   | 201812 | FR-IDF-0001 | 1.3   | 1.45  |
      | i_2   | 201901 | FR-IDF-0001 | 2.5   | 3     |

    # ------------------------------------------------------------------------------------------------------------------

    # alternative version
    # 1 table per indicator

    And indicator "i_1" should have values:
      | id  | time   | place       | value | goal |
      | --  | ----   | -----       | ----- | ---- |
      | i_1 | 201901 | FR-IDF-0001 | 1.2   | 1.55 |
      | i_1 | 201812 | FR-IDF-0001 | 1.3   | 1.45 |

    And indicator "i_2" should have values:
      | id  | time   | place       | value | goal |
      | --  | ----   | -----       | ----- | ---- |
      | i_2 | 201901 | FR-IDF-0001 | 2.5   | 3    |

    # ==================================================================================================================

    And I should get related indicators:
      | indicator | id   | name         | description                      |
      | --------- | --   | ----         | -----------                      |
      | i_1       | i_50 | Indicator 50 | The description for Indicator 50 |
      | i_2       | i_51 | Indicator 51 | The description for Indicator 51 |

    And I should get related indicators values:
      | id   | time   | place       | value | goal |
      | --   | ----   | -----       | ----- | ---- |
      | i_51 | 201901 | FR-IDF-0001 | 1.5   | 2    |


  # technical scenario
  Scenario: Get list of indicators

    When I call the webservice with:
    """
    {
       "indicators":[
          "i_1",
          "i_2"
       ],
       "time":{
          "type":"range",
          "start":{
             "type":"m",
             "value":"201801"
          },
          "stop":{
             "type":"m",
             "value":"201901"
          }
       },
       "places":[
          "FR-IDF-0001"
       ],
       "options":{
          "description":true,
          "related":true,
          "components":false
       }
    }
    """

    Then I should have following response:
    """
    {
      "indicators":[
        {
          "id":"i_1",
          "name":"Indicator 01",
          "description":"The description for Indicator 01",
          "values":[
            {
              "time":{
                "type":"m",
                "value":201901
              },
              "place":"FR-IDF-0001",
              "value":1.2,
              "goal":1.55
            },
            {
              "time":{
                "type":"m",
                "value":201812
              },
              "place":"FR-IDF-0001",
              "value":1.3,
              "goal":1.45
            }
          ],
          "related":[
            {
              "id":"i_50",
              "name":"Indicator 50",
              "description":"The description for Indicator 50",
              "values":[

              ]
            }
          ],
          "components":[]
        },
        {
          "id":"i_2",
          "name":"Indicator 02",
          "description":"The description for Indicator 02",
          "values":[
            {
              "time":{
                "type":"m",
                "value":201901
              },
              "place":"FR-IDF-0001",
              "value":2.5,
              "goal":3
            }
          ],
          "related":[
            {
              "id":"i_51",
              "name":"Indicator 51",
              "description":"The description for Indicator 51",
              "values":[
                {
                  "time":{
                    "type":"m",
                    "value":201901
                  },
                  "place":"FR-IDF-0001",
                  "value":1.5,
                  "goal":2
                }
              ]
            }
          ],
          "components":[]
        }
      ]
    }
    """
