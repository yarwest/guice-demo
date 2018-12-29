# Guice demo
A demo and explanation for using Guice for dependency injection in a Java application. Some of the knowledge is aimed specifically at Guice but a few things are more widely applicable.

Java/OO knowledge is basically a must to be able to follow along.

The `src` directory contains example code. The first example in the [calculator](https://github.com/yarwest/guice-demo/tree/master/src/main/java/com/yarwest/guice_demo/calculator) package is a JavaFX application with a very simple calculator implemented. This example has some additional configuration going on in the `start` method to properly initiate a JavaFX window.

The second example in the [my_class_example](https://github.com/yarwest/guice-demo/tree/master/src/main/java/com/yarwest/guice_demo/my_class_example) package is a simpler program without a GUI that prints output directly to `System.out`.

## The goal
The point of dependency injection is to reduce coupling between parts of software.

This allows for easier replacement of implementations/dependencies and improves testability by making it easier to replace/stub certain code.

It is also a best practice to limit your dependency on whatever tool used for dependency injection, since referencing, for example, Guice throughout your code base will make it harder to switch to a different tool/framework at a later stage.

## How does it work
What Guice does is create instances of objects that are used in code based on a specific configuration. A Guice `Injector` creates the instances that are requested and whenever that instance is also reliant on a specific injection the `Injector` takes care of that right away. This basically creates an entire chain of injections that are being handled automatically.

One thing that has to be kept in mind is that the chain can not be interrupted or some parts of the application may not be processed by the `Injector` resulting in null pointers or other problems.

## The basics
The most basic form of dependency injection is as simple as declaring a class proprety and annotating it with the `@Inject` annotation. A simple example:

```Java
public class UIController {
  @Inject
  Calculator calculator

  public void handleButtonClick() {
    calculator.addition(1, 2);
  }
}
```

This example had no actual dependency on Guice as the `@Inject` annotations is part of the Java Extras package.

To make this controller actually work, we require a small amount of bootstrapping using Guice. I recommend including this somewhere in the start up (the 'entry point') of your application and limiting any actual relation to Guice to preferably one class.

```Java
public void bootstrap() {
  Injector injector = Guice.createInjector();
  UIController controller = injector.getInstance(UIController.class);
  controller.handleButtonClick();
}
```
This method creates a Guice injector, which in turn is used to create an instance of the earlier created `UIController`. By using the injector to get an instance of our class, it looks in said class for any `@Inject` annotations and resolves them for us. After which we can safely call our `handleButtonClick()` method without any issues regarding a uninitialized `Calculator` property.

As you can see no actual configuration needs to be done in this example because we bind a reqular Java `class` so Guice will find said class and will use that no questions asked. This does not reduce the coupling between the `UIController` and the `Calculator` class by much.

The most common use for dependency injection however relies on using an `interface` and this decouples the two components a bit more. The example is very similar to the previous one:

```Java
public class Calculator {

  @Inject private IOperation additionOperation;

  public int addition(int firstNumberInput, int secondNumberInput) {
    return additionOperation.calculate(firstNumberInput, secondNumberInput);
  }
}
```

So in this example we want to inject an implementation of the `IOperation` interface. Guice can not make out which implementation we want to use here (if any exist to begin with). To make that clear we will need to create a binding which binds the `IOperation` interface to the `Addition` class which implements it:

```Java
bind(IOperation.class).to(Addition.class);
```

This binding needs to be placed in an instance of `AbstractModule` which would look like this:

```Java
public class BootstrapModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(IOperation.class).to(Addition.class);
  }
}
```

Whenever you want to use a different implementation of `IOperation` you can just replace `Addition.class` with any other class that implements the same interface and it will work right away. This prevents having to refactor every reference to the specific implementation throughout your codebase.

Now to actually tell Guice to use this particular configuration we have to modify our `bootstrap()` method from earlier to actually pass our `BootstrapModule` to be used by the injector.

```Java
public void bootstrap() {
  Injector injector = Guice.createInjector(new BootstrapModule());
  UIController controller = injector.getInstance(UIController.class);
  controller.handleButtonClick();
}
```
And that connects the various classed together properly.

The [JUnit4 test package](https://github.com/yarwest/guice-demo/tree/master/src/test/java/com/yarwest/guice_demo) shows how you can easily sumplement an binding with a stub by creating an anonymous instance of the `AbstractModule` inside your unit test build up. For example:

```Java
public class CalculatorTest {
  private Calculator calc;

  @BeforeEach
  public void setup() {
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(IOperation.class).to(AdditionStub.class);
      }
    });
    calc = injector.getInstance(Calculator.class);
  }

  @Test
  public void calculatorTest() {
    assertEquals(calc.addition(3, 9), 39);
  }
}
```

## Advanced
That was the simple part, now there is a lot more that can be done using Guice and JavaX so I will showcase a few neat tricks.

### Named injections
The first one is using the `@Named` annotation. This can be used to more specifically bind implementations to `@Inject` statements. The `Calculator` example from before could be modified to support multiple operations while still using the same `IOperation` interface:

```Java
public class Calculator {

  @Inject @Named("Addition Operation") private IOperation additionOperation;
  @Inject @Named("Subtraction Operation") private IOperation subtractionOperation;
  @Inject @Named("Multiplication Operation") private IOperation multiplicationOperation;
  @Inject @Named("Division Operation") private IOperation divisionOperation;

  ...
}
```

This requires a more specific binding to actually take into consideration the naming of the property:

```Java
bind(IOperation.class).annotatedWith(Names.named("Addition Operation")).to(Addition.class);
```

Which in turn leads to a configuration that looks like this:

```Java
public class BootstrapModule extends AbstractModule {

  @Override
  protected void configure() {
	  bind(IOperation.class).annotatedWith(Names.named("Addition Operation")).to(Addition.class);
	  bind(IOperation.class).annotatedWith(Names.named("Subtraction Operation")).to(Subtraction.class);
	  bind(IOperation.class).annotatedWith(Names.named("Multiplication Operation")).to(Multiplication.class);
	  bind(IOperation.class).annotatedWith(Names.named("Division Operation")).to(Division.class);
  }
}
```

For a more detailed look into the classes defintely check out the [calculator package](https://github.com/yarwest/guice-demo/tree/master/src/main/java/com/yarwest/guice_demo/calculator).

### Constructor injection
The earlier examples were all based on field injection but that is not the only way to set properties of a class via dependency injection. One can also utilize constructor injection, which works roughly the same way.

Instead of having the properties being injected seperately, they get declared normally and get injected together in the constructor, this would look as follows:

```Java
public class UIController {
  
  private Calculator calculator;

  @Inject
  public UIController(Calculator calculator) {
    this.calculator = calculator;
  }
}
```
As you can see this does require a bit more code but provides a slight performance boost since your dependency injection framework does not have to perform seperate (reflection) operations for each property that has to be injected.

Another factor is compatibility as certain Java platforms do not support reflection.

Last but not least it makes it clearer what dependencies a particular class has just from seeing the constructor and its signature and allows you to create an instance yourself without having to rely on any tools by simply calling the constructor and not having to worry about fields that are to be set but can't immediately be seen or accessed.

### Providers
As mentioned before, it is vital that the chain of injections is not broken. This is easy enough with an `@Inject` when one class relies on a set number of instances of another class, but when this number is unknown/dynamic a simple property and annotation will not suffice.

This can be solved by using a `Provider`, which is another `javax` gem. This `Provider` essentially serves as a factory that can create instances of a specific class. All that has to be done is injecting it and the best way that can be done is by performing a constructor injection as demonstrated below:

```Java
public class MyClassFactory {
	private final Provider<MyClass> myClassProvider;

	private List<MyClass> classes = new ArrayList<>();

	@Inject
	public MyClassFactory(Provider<MyClass> myClassProvider) {
		this.myClassProvider = myClassProvider;
	}

  ...
}
```

Summarized, this class has a `Provider` property that can create `MyClass` instances and this `Provider` is set in the constructor.

To use the provider and get an instance of `MyClass` all that has to be done is calling the `get()` method:
```Java
public class MyClassFactory {
  ...

  public MyClass getClassInstance() {
    return myClassProvider.get();
  }
}
```

This way every instance of `MyClass` will have it's injections resolved and the chain will live on.

The `MyClassFactory` can be found in the [my_class_example](https://github.com/yarwest/guice-demo/tree/master/src/main/java/com/yarwest/guice_demo/my_class_example) for a better look at using a `Provider`.