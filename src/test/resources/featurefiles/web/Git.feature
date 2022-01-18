Feature: validate the git hub repo using ui and api

  @github
  Scenario Outline: validate the git hub repo with star count
    Given I navigate to git hub application
    When I navigate to SearchResultPage after search the <product>
    Then I click on the user search link
    And I click on the user
    Then I click on the first repo
    And I validate the count of the star for ui and api response for the <api>

    Examples:
      |product | user         | api|
      |torvalds|Linus Torvalds|repos/torvalds/linux|