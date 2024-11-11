// 这是一种 Factory 模式


// 1. 定义接口
public interface Shape {
	void draw();
}

// 2. 实现具体的类
public class Square implements Shape {		// Square 类
	@Override
	public void draw() {
        System.out.println("  ----");
        System.out.println(" |    |");
        System.out.println(" |    |");
        System.out.println("  ----");
	}
}

public class Triangle implements Shape {	// Triangle 类
    @Override
    public void draw() {
        System.out.println("   /\\");
        System.out.println("  /  \\");
        System.out.println(" /____\\");
    }
}

public class Rectangle implements Shape {	// Rectangle 类
    @Override
    public void draw() {
        System.out.println("  ----");
        System.out.println(" |    |");
        System.out.println("  ----");
    }
}

// 3. 创建 ShapeFactory 类
@Data
public class ShapeFactory {
	// 工厂方法，用于根据类型生成正确的 Shape 对象
	public Shape getShape(String shapeType) throws Exception {
		if (shapeType == null) {
			throw new Exception("No specified shape!");
		}

		if (shapeType.equalsIgnoreCase("Square")) {
            return new Square();
        } else if (shapeType.equalsIgnoreCase("Triangle")) {
            return new Triangle();
        } else if (shapeType.equalsIgnoreCase("Rectangle")) {
            return new Rectangle();
        }

        return null;
	}
}























