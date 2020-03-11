Feature: Get indicators

  In order to know things
  A manager needs to get indicators

  Scenario: Get list of indicators

    When I ask for indicators:
      | ids      | places      | time            | with label  | with related | with components |
      | ---      | -----       | ----            | ----------- | ------------ | --------------- |
      | i_1, i_2 | FR-IDF-0001 | 201801 - 201901 | Y           | Y            | N               |

    # ==================================================================================================================
    # indicators

    Then I should get list of indicators:
      | id  | name         |
      | --  | ----         |
      | i_1 | Indicator 01 |
      | i_2 | Indicator 02 |

    # ==================================================================================================================
    # indicator values

    # all in one table

    And I should get indicator values:
      | indicator id | place       | time   | value | goal  |
      | -----        | -----       | -----  | ----- | ----- |
      | i_1          | FR-IDF-0001 | 201901 | 1.2   | 1.55  |
      | i_1          | FR-IDF-0001 | 201812 | null  | 1.45  |
      | i_2          | FR-IDF-0001 | 201901 | 2.5   | 3     |

    # alternate: one table per indicator

    And indicator "i_1" should have values:
      | place       | time   | value | goal |
      | -----       | ----   | ----- | ---- |
      | FR-IDF-0001 | 201901 | 1.2   | 1.55 |
      | FR-IDF-0001 | 201812 | null  | 1.45 |

    And indicator "i_2" should have values:
      | place       | time   | value | goal |
      | -----       | ----   | ----- | ---- |
      | FR-IDF-0001 | 201901 | 2.5   | 3    |

    # ==================================================================================================================
    # related

    # all in one table

    And I should get related indicators:
      | indicator id | id   | name         |
      | ---------    | --   | ----         |
      | i_1          | i_50 | Indicator 50 |
      | i_2          | i_51 | Indicator 51 |

    # alternate: one table per indicator

    And indicator "i_1" should have related:
      | id   | name         |
      | --   | ----         |
      | i_50 | Indicator 50 |

    And indicator "i_2" should have related:
      | id   | name         |
      | --   | ----         |
      | i_51 | Indicator 51 |

    # ==================================================================================================================
    # Related values

    # all in one table

    And I should get related indicators values:
      | indicator id | place       | time   | value | goal |
      | --           | -----       | ----   | ----- | ---- |
      | i_51         | FR-IDF-0001 | 201901 | 1.5   | 2    |

    # alternate: one table per related

    And related indicator "i_51" should have values:
      | place       | time   | value | goal |
      | -----       | ----   | ----- | ---- |
      | FR-IDF-0001 | 201901 | 1.5   | 2    |

