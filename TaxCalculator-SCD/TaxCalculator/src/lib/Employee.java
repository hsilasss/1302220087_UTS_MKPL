package lib;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	private static final int salary_grade1 = 3000000;
	private static final int salary_grade2 = 5000000;
	private static final int salary_grade3 = 7000000;
	private static final double multiplier = 1.5;
	private static final int fullYear_month = 12;

	private String employeeId;
	private String firstName;
	private String lastName;
	private String idNumber;
	private String address;
	
	private int yearJoined;
	private int monthJoined;
	private int dayJoined;
	private int monthWorkingInYear;
	
	private boolean isForeigner;
	private boolean gender; //true = Laki-laki, false = Perempuan
	
	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;
	
	private String spouseName;
	private String spouseIdNumber;

	private List<String> childNames;
	private List<String> childIdNumbers;
	
	
	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address, int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, boolean gender) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.address = address;
		this.yearJoined = yearJoined;
		this.monthJoined = monthJoined;
		this.dayJoined = dayJoined;
		this.isForeigner = isForeigner;
		this.gender = gender;
	}

	public class Child {
		private String name;
		private String idNumber;
		
		public Child(String name, String idNumber) {
			this.name = name;
			this.idNumber = idNumber;
		}
	}

	private List<Child> children = new LinkedList<>();
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	 public void setMonthlySalary(int grade) {
		int baseSalary;
		switch (grade) {
			case 1: baseSalary = salary_grade1; break;
			case 2: baseSalary = salary_grade2; break;
			case 3: baseSalary = salary_grade3; break;
			default: baseSalary = 0; break;
		}
	
		if (isForeigner) {
			baseSalary *= multiplier;
		}
	
		this.monthlySalary = baseSalary;
	}
	
	
	public void setAnnualDeductible(int deductible) {	
		this.annualDeductible = deductible;
	}
	
	public void setAdditionalIncome(int income) {	
		this.otherMonthlyIncome = income;
	}
	
	public void setSpouse(String spouseName, String spouseIdNumber) {
		this.spouseName = spouseName;
		this.spouseIdNumber = spouseIdNumber;
	}
	
	public void addChild(String name, String idNumber) {
		children.add(new Child(name, idNumber));
	}
	
	public int getAnnualIncomeTax() {
		
		//Menghitung berapa lama pegawai bekerja dalam setahun ini, jika pegawai sudah bekerja dari tahun sebelumnya maka otomatis dianggap 12 bulan.
		LocalDate date = LocalDate.now();
		
		if (date.getYear() == yearJoined) {
			monthWorkingInYear = date.getMonthValue() - monthJoined;
		}else {
			monthWorkingInYear = fullYear_month;
		}
		
		return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthWorkingInYear, annualDeductible, spouseIdNumber.equals(""), childIdNumbers.size());
	}


}
