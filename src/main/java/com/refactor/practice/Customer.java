package com.refactor.practice;

import com.google.common.collect.Lists;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

public class Customer {
	private String _name;
	private Vector _rentals = new Vector();

	public Customer(String _name, Vector _rentals) {
		this._name = _name;
		this._rentals = _rentals;
	}

	public String getName() {
		return _name;
	}

	public void addRental(Rental arg) {
		_rentals.add(arg);
	}

	public String statement() {
		double totalAmount = 0;
		int frequentRenterPoints = 0;
		Enumeration rentals = _rentals.elements();
		String result = "Rental Record for " + getName() + "\n";
		while (rentals.hasMoreElements()) {
			double thisAmount = 0D;
			Rental each = (Rental) rentals.nextElement();

			//determine amounts for each line
			switch (each.getMovie().getPriceCode()) {
				case Movie.REGULAR:
					thisAmount += 2;
					if (each.getDayRented() > 2) {
						thisAmount += (each.getDayRented() - 2) * 1.5;
					}
					break;
				case Movie.NEW_RELEASE:
					thisAmount += each.getDayRented() * 3;
					break;
				case Movie.CHILDRENS:
					thisAmount += 1.5;
					if (each.getDayRented() > 3) {
						thisAmount += (each.getDayRented() - 3) * 1.5;
					}
					break;
			}
			//add frequent renter points
			frequentRenterPoints++;
			//add bonus for a two day new release rental
			if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) &&
					each.getDayRented() > 1) {
				frequentRenterPoints++;
			}

			//show figures for this rental
			result += "\t" + each.getMovie().getTitle() +
					"\t" + String.valueOf(thisAmount) + "\n";
			totalAmount += thisAmount;
		}
		//add footer lines
		result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
		result += "You earned" + String.valueOf(frequentRenterPoints) +
				" frequent renter points";
		return result;
	}
}
