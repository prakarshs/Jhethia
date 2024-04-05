# Jhethia Programming Language Build With Java
<p align="center">
  <img src="https://images.pexels.com/photos/20693518/pexels-photo-20693518.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1" alt="jhethia" width="630" height="400">
</p>

## Samples
1. Program to create a struct Person and if is_developer is true print person's experience
2. Terminal response to the HelloWorld.jhethia code
3. Fun Error Handling
<p align="center">
  <img src="https://github.com/prakarshs/Jhethia/assets/138369731/a8e2c16d-9649-4422-b11d-9512ceb12b80" alt="jhethia_hello_world" width="500" height="400">
</p>
<p align="center">
  <img src="https://github.com/prakarshs/Jhethia/assets/138369731/4c1d1ba1-5a3b-42a6-b2e3-c46d1bfd616e" alt="jgethia_result" width="500" height="200">
</p>
<p align="center">
  <img src="https://github.com/prakarshs/Jhethia/assets/138369731/1208d1e7-46be-4c9f-85b1-06aa07cdc656" alt="jhethia_error" width="500" height="250">
</p>


## Syntax
### Basic constructions

1. Input from console

```
lijiye_baapuji <variable_name>
lijiye_baapuji name
```

2. Print to console
```
dekhiye_baapuji <expression>

dekhiye_baapuji "Chai Piyo, Biscuit Khao!"
```
3. Variables declaration
- ##### Plain Type
```
<variable_name> = <expression>

a1 = 123
a2 = "Good Morning Babita Ji!"
```
- ##### Struct Instance
```
- dhancha <struct name>
    yeh_lo arg1
    yeh_lo arg1
    ...
khatam

dhancha Student
    yeh_lo name
    yeh_lo age
    yeh_lo plays_football
khatam

- <variable name> = naya <struct_name> [ <argument_expression_1> <argument_expression_2> ... ]

student1 = new Student [ "Tapu" 23 sahi_baat_hai ]
student2 = new Student [ "Goli" 25 galat_baat_hai]

boolean : sahi_baat_hai(true)/galat_baat_hai(false)

```

4. Conditions
```
agar <condition> toh
    # statements
khatam

agar student1 ka age>24 toh
    dekhiye_baapuji "Age is greater than 24."
khatam 
```
---
### Run this program
Firstly, compile and package the program into a uber jar, go to the ./target folder and then run the following command
```
java -cp Jhethia.jar org.prakarshs.JhethiaRun src/test/resources/hello_world.jhethia
```
You can add your own .(dot)jhethia files and run the code from the test/resources/{your_file} location
