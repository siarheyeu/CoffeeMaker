/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 *
 * Permission has been explicitly granted to the University of Minnesota
 * Software Engineering Center to use and distribute this source for
 * educational purposes, including delivering online education through
 * Coursera or other entities.
 *
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including
 * fitness for purpose.
 *
 *
 * Modifications
 * 20171114 - Ian De Silva - Updated to comply with JUnit 4 and to adhere to
 * 							 coding standards.  Added test documentation.
 */
package edu.ncsu.csc326.coffeemaker;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Unit tests for CoffeeMaker class.
 *
 * @author Sarah Heckman
 * modify by Saruj Sattayanurak
 */
public class CoffeeMakerTest {

	/**
	 * The object under test.
	 */
	private CoffeeMaker coffeeMaker;
	private Inventory inventory;

	// Sample recipes to use in testing.
	private Recipe recipe1, recipe1Copy, recipe2, recipe3, recipe4, recipe5;

	/**
	 * Method to create Recipe instance
	 * @param name name of recipe
	 * @param chocolate amount of chocolate for recipe
	 * @param coffee amount of coffee for recipe
	 * @param milk amount of milk for the recipe
	 * @param sugar amount of sugar for the recipe
	 * @param price price of recipe
	 * @return Recipe instance
	 * @throws RecipeException
	 */
	private Recipe createRecipe(String name, String chocolate, String coffee, String milk, String sugar, String price) throws RecipeException{
		Recipe r = new Recipe();
		r.setName(name);
		r.setAmtChocolate(chocolate);
		r.setAmtCoffee(coffee);
		r.setAmtMilk(milk);
		r.setAmtSugar(sugar);
		r.setPrice(price);
		return r;
	}

	/**
	 * Initializes some recipes to test with and the {@link CoffeeMaker}
	 * object we wish to test.
	 *
	 * @throws RecipeException  if there was an error parsing the ingredient
	 * 		amount when setting up the recipe.
	 */
	@Before
	public void setUp() throws RecipeException{

		coffeeMaker = new CoffeeMaker();
		inventory = new Inventory();

		//Set up for recipe1
		recipe1 = createRecipe("Coffee", "0","3","1","1","50");

		//Set up for recipe1Copy which is a recipe that have same recipe's name as recipe1
		recipe1Copy = createRecipe("Coffee", "1","1","2","2","10");

		//Set up for recipe2
		recipe2 = createRecipe("Mocha", "20","3","1","1","75");

		//Set up for recipe3
		recipe3 = createRecipe("Latte", "0","3","3","1","100");

		//Set up for recipe4
		recipe4 = createRecipe("Hot Chocolate", "4","0","1","1","65");

		//Set up for recipe5
		recipe5 = createRecipe("Extra", "16","16","16","16","200");
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	/**
	 * Test Case ID: 1
	 * Given a coffee maker with new recipes
	 * When we add 1 valid recipe
	 * Then the recipe will be valid to added to the system
	 */
	@Test
	public void testAddSingleValidRecipe(){
		assertTrue(coffeeMaker.addRecipe(recipe1));
	}

	/**
	 * Test Case ID: 2
	 * Given a coffee maker with new recipes
	 * When we add 3 recipes
	 * Then all recipe will be added to the system
	 */
	@Test
	public void testAddThreeValidRecipe(){
		assertTrue(coffeeMaker.addRecipe(recipe1));
		assertTrue(coffeeMaker.addRecipe(recipe2));
		assertTrue(coffeeMaker.addRecipe(recipe3));
	}

	/**
	 * Test Case ID: 3
	 * Given a coffee maker with new recipes
	 * When we add 4 recipes
	 * Then the fourth recipe will not be added to the system
	 */
	@Test
	public void testAddTooMuchValidRecipe(){
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.addRecipe(recipe3);
		assertFalse(coffeeMaker.addRecipe(recipe4));
	}

	/**
	 * Test Case ID: 4
	 * Given a coffee maker with new recipes
	 * When we add a new recipe with the name that already exists in the system
	 * Then the recipe will not be added to the system
	 */
	@Test
	public void testAddDuplicateNameRecipe(){
		coffeeMaker.addRecipe(recipe1);
		// recipe1Copy is a recipe that have same recipe's name as recipe1 ("Coffee")
		// but other properties are different
		assertFalse(coffeeMaker.addRecipe(recipe1Copy));
	}

	/**
	 * Test Case ID: 5
	 * Given a coffee maker with recipe number to delete
	 * When we pick a valid recipe number
	 * Then the program should return name of deleted recipe
	 */
	@Test
	public void testDeleteValidRecipe(){
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.addRecipe(recipe3);
		assertEquals("Coffee", coffeeMaker.deleteRecipe(0));
	}

	/**
	 * Test Case ID: 6
	 * Given a coffee maker with recipe number to delete
	 * When we pick an empty slot number
	 * Then the program should return null
	 */
	@Test
	public void testDeleteEmptySlotRecipe(){
		assertEquals(null, coffeeMaker.deleteRecipe(0));
	}

	/**
	 * Test Case ID: 7
	 * Given a coffee maker with only 1 recipe
	 * When we delete the recipe that slot should be empty
	 * Then if we delete that slot again the program should return null
	 */
	@Test
	public void testDeleteDeletedRecipe(){
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.deleteRecipe(0);
		assertEquals(null, coffeeMaker.deleteRecipe(0));
	}

	/**
	 * Test Case ID: 8
	 * Given a coffee maker with recipe number to edit
	 * When we pick a valid recipe number
	 * Then the program should return recipe name
	 */
	@Test
	public void testEditValidRecipe() throws RecipeException{
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.addRecipe(recipe3);
		Recipe newRecipe = new Recipe();
		newRecipe.setName("Latte");
		newRecipe.setAmtChocolate("20");
		newRecipe.setAmtCoffee("30");
		newRecipe.setAmtMilk("10");
		newRecipe.setAmtSugar("10");
		newRecipe.setPrice("500");
		assertEquals(coffeeMaker.editRecipe(2, newRecipe ), "Latte");
	}

	/**
	 * Test Case ID: 9
	 * Given a coffee maker with recipe number to edit
	 * When we pick an empty recipe number
	 * Then the program should return null
	 */
	@Test
	public void testEditEmptyRecipe() throws RecipeException{
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		Recipe newRecipe = new Recipe();
		newRecipe.setName("Latte");
		newRecipe.setAmtChocolate("20");
		newRecipe.setAmtCoffee("30");
		newRecipe.setAmtMilk("10");
		newRecipe.setAmtSugar("10");
		newRecipe.setPrice("500");
		assertNull(coffeeMaker.editRecipe(2, newRecipe ));
	}

	/**
	 * Test Case ID: 10
	 * Given a coffee maker with recipe number to edit
	 * When we pick a valid recipe number and change recipe name
	 * Then the program should not change it
	 */
	@Test
	public void testEditRecipeName() throws RecipeException{
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.addRecipe(recipe3);
		Recipe newRecipe = new Recipe();
		newRecipe.setName("New Latte");
		newRecipe.setAmtChocolate("20");
		newRecipe.setAmtCoffee("30");
		newRecipe.setAmtMilk("10");
		newRecipe.setAmtSugar("10");
		newRecipe.setPrice("500");
		coffeeMaker.editRecipe(2, newRecipe );
		assertEquals("Latte", coffeeMaker.getRecipes()[2].getName());
	}

	/**
	 * Test Case ID: 11
	 * Given a coffee maker with 1 recipe
	 * When we delete the recipe
	 * Then we will not be able to edit it
	 */
	@Test
	public void testEditAfterDelete() throws RecipeException{
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.deleteRecipe(0);
		Recipe newRecipe = new Recipe();
		newRecipe.setName("New Latte");
		newRecipe.setAmtChocolate("20");
		newRecipe.setAmtCoffee("30");
		newRecipe.setAmtMilk("10");
		newRecipe.setAmtSugar("10");
		newRecipe.setPrice("500");
		assertNull(coffeeMaker.editRecipe(0, newRecipe ));
	}

	/**
	 * Test Case ID: 12
	 * Given a coffee maker with the default inventory
	 * When we add coffee with well-formed quantities
	 * Then we do not get an exception trying to read the inventory quantities.
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddValidCoffeeAmt() throws InventoryException {
		coffeeMaker.addInventory("5","0","0","0");
	}

	/**
	 * Test Case ID: 13
	 * Given a coffee maker with the default inventory
	 * When we add milk with well-formed quantities
	 * Then we do not get an exception trying to read the inventory quantities.
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddValidMilkAmt() throws InventoryException {
		coffeeMaker.addInventory("0","5","0","0");
	}

	/**
	 * Test Case ID: 14
	 * Given a coffee maker with the default inventory
	 * When we add sugar with well-formed quantities
	 * Then we do not get an exception trying to read the inventory quantities.
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddValidSugarAmt() throws InventoryException {
		coffeeMaker.addInventory("0","0","5","0");
	}

	/**
	 * Test Case ID: 15
	 * Given a coffee maker with the default inventory
	 * When we add chocolate with well-formed quantities
	 * Then we do not get an exception trying to read the inventory quantities.
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddValidChocolateAmt() throws InventoryException {
		coffeeMaker.addInventory("0","0","0","5");
	}

	/**
	 * Test Case ID: 16
	 * Given a coffee maker with the default inventory (all 15)
	 * When we add coffee by 5
	 * Then 5 will add to 15 make it 20
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testInventoryIsAddToCurrent() throws InventoryException {
		coffeeMaker.addInventory("5","0","0","0");
		assertEquals("Coffee: " + "20" + "\n" + "Milk: " + "15" + "\n" + "Sugar: " + "15" + "\n"
				+ "Chocolate: " + "15" + "\n", coffeeMaker.checkInventory());
	}

	/**
	 * Test Case ID: 17
	 * Given a coffee maker with the default inventory
	 * When we add coffee with decimal-formed quantities
	 * Then we do get an InventoryException
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddDecimalCoffeeAmt() throws InventoryException {
		expectedException.expect(InventoryException.class);
		expectedException.expectMessage("Units of coffee must be a positive integer");
		coffeeMaker.addInventory("5.5","0","0","0");
	}

	/**
	 * Test Case ID: 18
	 * Given a coffee maker with the default inventory
	 * When we add milk with decimal-formed quantities
	 * Then we do get an InventoryException
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddDecimalMilkAmt() throws InventoryException {
		expectedException.expect(InventoryException.class);
		expectedException.expectMessage("Units of milk must be a positive integer");
		coffeeMaker.addInventory("0","5.5","0","0");
	}

	/**
	 * Test Case ID: 19
	 * Given a coffee maker with the default inventory
	 * When we add sugar with decimal-formed quantities
	 * Then we do get an InventoryException
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddDecimalSugarAmt() throws InventoryException {
		expectedException.expect(InventoryException.class);
		expectedException.expectMessage("Units of sugar must be a positive integer");
		coffeeMaker.addInventory("0","0","5.5","0");
	}

	/**
	 * Test Case ID: 20
	 * Given a coffee maker with the default inventory
	 * When we add chocolate with decimal-formed quantities
	 * Then we do get an InventoryException
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddDecimalChocolateAmt() throws InventoryException {
		expectedException.expect(InventoryException.class);
		expectedException.expectMessage("Units of chocolate must be a positive integer");
		coffeeMaker.addInventory("0","0","0","5.5");
	}

	/**
	 * Test Case ID: 21
	 * Given a coffee maker with the default inventory
	 * When we add coffee with negative-formed quantities
	 * Then we do get an InventoryException
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddNegativeCoffeeAmt() throws InventoryException {
		expectedException.expect(InventoryException.class);
		expectedException.expectMessage("Units of coffee must be a positive integer");
		coffeeMaker.addInventory("-5","0","0","0");
	}

	/**
	 * Test Case ID: 22
	 * Given a coffee maker with the default inventory
	 * When we add milk with negative-formed quantities
	 * Then we do get an InventoryException
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddNegativeMilkAmt() throws InventoryException {
		expectedException.expect(InventoryException.class);
		expectedException.expectMessage("Units of milk must be a positive integer");
		coffeeMaker.addInventory("0","-5","0","0");
	}

	/**
	 * Test Case ID: 23
	 * Given a coffee maker with the default inventory
	 * When we add sugar with negative-formed quantities
	 * Then we do get an InventoryException
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddNegativeSugarAmt() throws InventoryException {
		expectedException.expect(InventoryException.class);
		expectedException.expectMessage("Units of sugar must be a positive integer");
		coffeeMaker.addInventory("0","0","-5","0");
	}

	/**
	 * Test Case ID: 24
	 * Given a coffee maker with the default inventory
	 * When we add chocolate with negative-formed quantities
	 * Then we do get an InventoryException
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddNegativeChocolateAmt() throws InventoryException {
		expectedException.expect(InventoryException.class);
		expectedException.expectMessage("Units of chocolate must be a positive integer");
		coffeeMaker.addInventory("0","0","0","-5");
	}

	/**
	 * Test Case ID: 25
	 * Given a coffee maker with the default inventory
	 * When we add coffee with alphabetic character-formed quantities
	 * Then we do get an InventoryException
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddAlphabeticCoffeeAmt() throws InventoryException {
		expectedException.expect(InventoryException.class);
		expectedException.expectMessage("Units of coffee must be a positive integer");
		coffeeMaker.addInventory("five","0","0","0");
	}

	/**
	 * Test Case ID: 26
	 * Given a coffee maker with the default inventory
	 * When we add milk with alphabetic character-formed quantities
	 * Then we do get an InventoryException
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddAlphabeticMilkAmt() throws InventoryException {
		expectedException.expect(InventoryException.class);
		expectedException.expectMessage("Units of milk must be a positive integer");
		coffeeMaker.addInventory("0","five","0","0");
	}

	/**
	 * Test Case ID: 27
	 * Given a coffee maker with the default inventory
	 * When we add sugar with alphabetic character-formed quantities
	 * Then we do get an InventoryException
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddAlphabeticSugarAmt() throws InventoryException {
		expectedException.expect(InventoryException.class);
		expectedException.expectMessage("Units of sugar must be a positive integer");
		coffeeMaker.addInventory("0","0","five","0");
	}

	/**
	 * Test Case ID: 28
	 * Given a coffee maker with the default inventory
	 * When we add chocolate with alphabetic character-formed quantities
	 * Then we do get an InventoryException
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddAlphabeticChocolateAmt() throws InventoryException {
		expectedException.expect(InventoryException.class);
		expectedException.expectMessage("Units of chocolate must be a positive integer");
		coffeeMaker.addInventory("0","0","0","five");
	}

	/**
	 * Test Case ID: 29
	 * Given a coffee maker with the default inventory
	 * When we select check inventory
	 * Then we get list of all inventories with it quantity
	 *
	 */
	@Test
	public void testCheckInventoryFormat() {
		assertEquals("Coffee: " + "15" + "\n" + "Milk: " + "15" + "\n" + "Sugar: " + "15" + "\n"
				+ "Chocolate: " + "15" + "\n", coffeeMaker.checkInventory());
	}

	/**
	 * Test Case ID: 30
	 * Given a coffee maker with the default inventory
	 * When we add inventory by 5 and select check inventory
	 * Then we get list of all inventories with it default quantity + 5
	 *
	 */
	@Test
	public void testCheckInventoryAfterAdd() throws InventoryException {
		coffeeMaker.addInventory("5","5","5","5");
		assertEquals("Coffee: " + "20" + "\n" + "Milk: " + "20" + "\n" + "Sugar: " + "20" + "\n"
				+ "Chocolate: " + "20" + "\n", coffeeMaker.checkInventory());
	}

	/**
	 * Test Case ID: 31
	 * Given a coffee maker with the default inventory
	 * When we purchase beverage and select check inventory
	 * Then we get list of all inventories with it quantity - used inventory
	 *
	 */
	@Test
	public void testCheckInventoryAfterPurchased(){
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.makeCoffee(0,50);
		assertEquals("Coffee: " + "12" + "\n" + "Milk: " + "14" + "\n" + "Sugar: " + "14" + "\n"
				+ "Chocolate: " + "15" + "\n", coffeeMaker.checkInventory());
	}

	/**
	 * Test Case ID: 32
	 * Given a coffee with 50 units price
	 * When we purchase beverage with 60 units
	 * Then we get return change at 10 units
	 *
	 */
	@Test
	public void testReturnChangeAfterPurchased() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(10, coffeeMaker.makeCoffee(0,60));
	}

	/**
	 * Test Case ID: 33
	 * Given a coffee maker with the default inventory
	 * When we purchase beverage and enter not enough money
	 * Then we get list of all inventories that remain the same
	 *
	 */
	@Test
	public void testCheckInventoryAfterNotEnoughMoney(){
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.makeCoffee(0,40);
		assertEquals("Coffee: " + "15" + "\n" + "Milk: " + "15" + "\n" + "Sugar: " + "15" + "\n"
				+ "Chocolate: " + "15" + "\n", coffeeMaker.checkInventory());
	}

	/**
	 * Test Case ID: 34
	 * Given a coffee with 50 units price
	 * When we purchase beverage with 40 units
	 * Then we get return change 40 units
	 *
	 */
	@Test
	public void testReturnChangeAfterNotEnoughMoney() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(40, coffeeMaker.makeCoffee(0,40));
	}

	/**
	 * Test Case ID: 35
	 * Given a coffee maker with the default inventory
	 * When we purchase beverage and coffee maker not have enough inventory
	 * Then we get list of all inventories that remain the same
	 *
	 */
	@Test
	public void testCheckInventoryAfterNotEnoughInventory(){
		coffeeMaker.addRecipe(recipe5);
		coffeeMaker.makeCoffee(0,200);
		assertEquals("Coffee: " + "15" + "\n" + "Milk: " + "15" + "\n" + "Sugar: " + "15" + "\n"
				+ "Chocolate: " + "15" + "\n", coffeeMaker.checkInventory());
	}

	/**
	 * Test Case ID: 36
	 * Given a coffee maker with the default inventory
	 * When we purchase beverage and coffee maker not have enough inventory
	 * Then we get return change back
	 *
	 */
	@Test
	public void testReturnChangeAfterNotEnoughInventory(){
		coffeeMaker.addRecipe(recipe5);
		assertEquals(300, coffeeMaker.makeCoffee(0,300));
	}

	/**
	 * Test Case ID: 37
	 * Given a coffee maker with new recipes
	 * When we add 1 valid recipe
	 * Then the recipe will be saved in recipe book
	 */
	@Test
	public void testGetRecipeAfterAddSingleValidRecipe(){
		coffeeMaker.addRecipe(recipe1);
		assertEquals(recipe1.getName(), coffeeMaker.getRecipes()[0].getName());
	}

	/**
	 * Test Case ID: 38
	 * Given a coffee maker with new recipes
	 * When we add 4 valid recipe
	 * Then the fourth recipe will not be saved in recipe book
	 */
	@Test
	public void testGetRecipeAfterAddTooMuchRecipe(){
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.addRecipe(recipe3);
		coffeeMaker.addRecipe(recipe4);
		assertEquals(3, coffeeMaker.getRecipes().length);
	}

	/**
	 * Test Case ID: 39
	 * Given a coffee maker with new recipes
	 * When we add recipe which have duplicate name
	 * Then the new recipe will not be added to recipe book
	 */
	@Test
	public void testGetRecipeAfterAddDuplicateRecipe(){
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe1Copy);
		assertEquals(null, coffeeMaker.getRecipes()[1]);
	}

	/**
	 * Test Case ID: 40
	 * Given a coffee maker 1 recipe
	 * When we delete the recipe and call method getRecipe
	 * Then the program should return null
	 */
	@Test
	public void testGetRecipeAfterDeleteRecipe(){
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.deleteRecipe(0);
		assertEquals(null, coffeeMaker.getRecipes()[0]);
	}

	/**
	 * Test Case ID: 41
	 * Given a coffee maker with recipe number to edit
	 * When we pick a valid recipe number
	 * Then the program should return recipe name
	 */
	@Test
	public void testGetRecipeAfterEditRecipe() throws RecipeException{
		coffeeMaker.addRecipe(recipe1);// name = "Coffee"
		Recipe newRecipe = new Recipe();
		newRecipe.setName("Latte");
		newRecipe.setAmtChocolate("20");
		newRecipe.setAmtCoffee("30");
		newRecipe.setAmtMilk("10");
		newRecipe.setAmtSugar("10");
		newRecipe.setPrice("500");
		coffeeMaker.editRecipe(0, newRecipe);
		// omit recipe name -> see test case id
		assertEquals(20, coffeeMaker.getRecipes()[0].getAmtChocolate());
		assertEquals(30, coffeeMaker.getRecipes()[0].getAmtCoffee());
		assertEquals(10, coffeeMaker.getRecipes()[0].getAmtMilk());
		assertEquals(10, coffeeMaker.getRecipes()[0].getAmtSugar());
		assertEquals(500, coffeeMaker.getRecipes()[0].getPrice());
	}

	/**
	 * Test Case ID: 56
	 * Given a coffee maker without any recipe in the system
	 * When purchase un-exist recipe
	 * Then purchase must fail and return full amount of money
	 */
	@Test
	public void testPurchaseEmptyRecipe() {
		assertEquals(300, coffeeMaker.makeCoffee(0,300));
	}

	/**
	 * Test Case ID: 57
	 * Given a coffee maker with default inventory
	 * When reset machine try set all inventory to 0
	 * Then all inventories turn to 0 and no error raise
	 */
	@Test
	public void testSetInventoryToNegative(){
		inventory.setCoffee(-1);
		inventory.setSugar(-1);
		inventory.setChocolate(-1);
		inventory.setMilk(-1);
		assertEquals("Coffee: " + "15" + "\n" + "Milk: " + "15" + "\n" + "Sugar: " + "15" + "\n"
				+ "Chocolate: " + "15" + "\n", coffeeMaker.checkInventory());
	}

}