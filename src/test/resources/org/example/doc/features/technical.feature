Feature: Get indicators

  In order to know things
  A manager needs to get indicators

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
              "value":null,
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
