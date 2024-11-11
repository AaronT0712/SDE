// 也要求是Factory模式；

public interface Toy {
	void talk();
}

public class Dog implements Toy {
	@Override
	public void talk() {
		System.out.println("Wow");
	}
}

public class Cat implements Toy {
	@Override
	public void talk() {
		System.out.println("Meow");
	}
}

public class ToyFactory {
	public Toy getToy(String toyType) throws Exception {
		if (toyType == null) {
			throw new Exception("No specified type!");
		}

		if (toyType.equalsIgnoreCase("Dog")) {
			return new Dog();
		} else if (toyType.equalsIgnoreCase("Cat")) {
			return new Cat();
		} else {
			throw new Exception("No matched type!");
		}
	}
}