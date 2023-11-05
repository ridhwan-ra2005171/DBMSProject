package test;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		ObjectMapper objectMapper = new ObjectMapper();
		List<Person> persons = objectMapper.readValue(new File("data.json"), new TypeReference<List<Person>>(){});

	}

}
