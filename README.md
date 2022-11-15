# Setup and run the project

The present repository stores the different java projects necessary to deploy and execute, service oriented and component-based applications specified with lazy services. The whole repository corresponds to an eclipse workspace constituted of three projects. By doing so, the easiest way to run the project is to clone the repository and used it as the workspace of an eclipse application. Here are the complete list of steps to setup the project in your computer:
- Download the latest version of eclipse for java here : https://www.eclipse.org/downloads/packages/release/2022-09/r/eclipse-ide-java-developers ;
- Clone (using *git clone*) or download the repository;
- Extract and launch eclipse using the folder of the cloned repository as the eclipse workspace;.

In order to test the behavior of lazy services, you can run the small example of  computer science school enrollment present in the repository. The example is described in the *Running example* section. Follow these steps to run the example:
- Run the first component by running the configuration *LauncherCA*;
- Run the second component by running the configuration *LauncherCB*;
- On the CA component run an enrollment process by clicking on the start button, and confirming with the Ok button;
- Once the process is started, you can process a service present in the graph by clicking on it and applying one of the proposed productions (a way to decompose/solve a service). For the specific case of remote service (service with dashed borders) the click action serves to send inputs to the component providing the service.

Note that for the enrollment example, some productions will wait for the definition of certain inputs such as *resume* or *debit*, before being usable.  You can play with other examples by changing the component and network specifications. The network specification is located at the root of the *starter* project. The components specifications are located in the same project in the folder *gag-specification*.

To know more about lazy services read the next sections.  

# Notion of lazy/incremental service


Basically, a 'lazy' service is a service that processes lazily its inputs ( as their values are needed in the service execution) and return incrementally its outputs (as their computation finish). This implies that a lazy service is capable of producing and output as soon as the subset of the inputs it depends on are available, regardless of the avaibility of other inputs. By doing so, such services are suitable for higher distributive environment where the computation is performed over internet with multiples inputs sources, corresponding themselves as outputs of other ongoing services. Besides they allows to easily combine user activities and machine activities in service execution. In this manner, user skill can be easily automated in a composite lazy service which is obtained by combining automated program and  user interactions. A typical form of a lazy service looks  like :
   
      
      
**ServiceName**( inputs ) ⟨ outputs ⟩ = {  

var = *ordinaryFunction* ( inputs ) ;   
**subService** ( inputs ) ⟨ outputs ⟩;   
varU = *userActivity* ( inputs );   
...  
}
   
      
      
 Stating that the composite service being defined is composed of a set of ordinary computations (for instance Rest, SOAP, BPEL, Open API services, or even compiled procedure of general purpose languages such as JAVA, C, C++); a set of sub lazy services and a set of executions implying user activities. From an implementation viewpoint, ordinary functions and user activities can be handled by call to features of pre-compiled or existing programms.
 
 # Running Example
 To better explain the behavior of a lazy service, let's play with the small example of course enrollment in a competitive computer science school. 
The process starts when a student wants to enroll to a certification session of the computer science school. In order to register, the student has first to fill in his personal information (last name, first name, email, etc), a resume and a cover letter that expresses in detail his motivation since the session is selective. This first step can be described by a service :
 
**Apply**( infos, resume,letter)⟨result,grantSuggestion ⟩,

 where the infos, resume and letter are the inputs of the service (the information coming from the student) and the result and grantSuggestion are the outputs of the service (the data it has to produce). The result represents the decision of the registration committee about the application, while the grantSuggestion are the grants that the committee recommends the student to apply in order to fund his training. Nevertheless the student can still pay his session with his debit card or obtain a grant from an organization not belonging to the suggestion. At the second stage, the student has to pay or obtain a grant and select one of the school classes (week day or weekend day) according to his availability. This second step can also be described by a service :
**Subscribe** (session, committeeDecision, debitCredential/Grant)⟨Receipt⟩.

In this manner, an intuitive way to define the composite service handling the enrollment process may be like this :  

**Register**( infos, letter, resume, class, debitCredential/Grant)⟨ decision, grantSuggestion,receipt⟩

=

{
**Apply**( infos,resume, letter )⟨decision, grantSuggestion⟩;

**Subscribe**( class, decision, debitCredential/Grant)⟨receipt⟩;

}

This intuitive specification, can then be translated to the current xml specification :

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<GAG name="My GAG">
	<service axiom="true" name="Register" >
	    <input name="personal information" shortName="infos" />
		<input name="cover letter" shortName="letter" />
		<input name="resume" shortName="resume" />
		<input name="class" shortName="class" />
		<input name="debit" shortName="debit" />
		
		<output name="decision" shortName="decision" />
		<output name="grant suggestion" shortName="suggestion" />
		<output name="receipt" shortName="receipt" />
		<production name="Process" subServices="Apply Subscribe">
		
			<semantic>
				<action>
					<leftpart service="Register" parameter="decision" />
					<rightpart
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						xsi:type="idExpression" service="Apply" parameter="decision" />
				</action>
				<action>
					<leftpart service="Register" parameter="grant suggestion" />
					<rightpart
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						xsi:type="idExpression" service="Apply" parameter="grant suggestion" />
				</action>
				<action>
					<leftpart service="Register" parameter="receipt" />
					<rightpart
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						xsi:type="idExpression" service="Subscribe" parameter="receipt" />
				</action>
				<action>
					<leftpart service="Apply" parameter="personal information" />
					<rightpart
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						xsi:type="idExpression" service="Register" parameter="personal information" />
				</action>
				
				<action>
					<leftpart service="Apply" parameter="cover letter" />
					<rightpart
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						xsi:type="idExpression" service="Register" parameter="cover letter" />
				</action>
				
				<action>
					<leftpart service="Apply" parameter="resume" />
					<rightpart
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						xsi:type="idExpression" service="Register" parameter="resume" />
				</action>
				<action>
					<leftpart service="Subscribe" parameter="decision" />
					<rightpart
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						xsi:type="idExpression" service="Apply" parameter="decision" />
				</action>
				<action>
					<leftpart service="Subscribe" parameter="class" />
					<rightpart
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						xsi:type="idExpression" service="Register" parameter="class" />
				</action>
				<action>
					<leftpart service="Subscribe" parameter="debit" />
					<rightpart
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						xsi:type="idExpression" service="Register" parameter="debit" />
				</action>
			</semantic>
		</production>
	</service>
	</GAG>
```

The code above describes the complete behavior of the composite service *register* without any arbitrary execution order of subservices. All that matters is the dependency relation of attribute which are defined by *semantic action*. The *xsi:type="idExpression"* it used to specify that two service parameter are equals. For instance the parameter decision of the service *Register* is equal to the parameter decision of the sub-service *Apply*. One can further refine the service *Apply* with its behavior with the following code.

```xml
<service name="Apply"  >
	 <input name="personal information" shortName="infos" />
		<input name="cover letter" shortName="letter" />
		<input name="resume" shortName="resume" />
        <output name="syst" shortName="syst" />		 
		<output name="decision" shortName="decision" />
		<output name="grant suggestion" shortName="suggestion" />
		<production name="decide" subServices="">
		<guard location="..\LocalFunctions\bin" method="cb.Apply.guardDecide" />
		 <semantic>
			  <function-declaration
					location="..\LocalFunctions\bin" method="cb.Apply.system" name="system" />
			  <function-declaration
					location="..\LocalFunctions\bin" method="cb.Apply.decide" name="decide" />
			 <function-declaration
					location="..\LocalFunctions\bin" method="cb.Apply.grant" name="grant" />
		    
				<action>
					<leftpart service="Apply" parameter="decision" />
					<rightpart
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						xsi:type="functionExpression" function="decide">
						<arg service="Apply" parameter="syst" />
					</rightpart>
				</action>
			<action>
					<leftpart service="Apply" parameter="syst" />
					<rightpart
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						xsi:type="functionExpression" function="system">
						<arg service="Apply" parameter="personal information" />
						<arg service="Apply" parameter="cover letter" />
						<arg service="Apply" parameter="resume" />
					</rightpart>
				</action>
				<action>
					<leftpart service="Apply" parameter="grant suggestion" />
					<rightpart
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						xsi:type="functionExpression" function="grant">
						<arg service="Apply" parameter="syst" />
					</rightpart>
				</action>
		 </semantic>
		
		</production>
		
		
	</service>

```
In the specification of service *Apply*, you notice the *xsi:type="functionExpression"* that is used to call ordinary precompiled programs to help the registration comittee. In the current case, the programs coded in java constitute the ordinary functions of the service *Apply*. They help the school comittee to decide and suggest grant about an application. As mentioned above, there is no predefined execution order of ordinary functions or subservices (when they exist). Each function executes as soon as it inputs are available. When it execution finishes the outputs it returns may authorized the execution of other waiting functions. Note also the xml element ** < guard > ** . *Guard* are used to control the activation of a decomposition according to the form of the service inputs. In the current case the guard *cb.Apply.guardDecide* will authorized the execution of the decomposition rule *decide* if the student has already provided his resume. However, more advanced guards can be defined according to the problem at hand. Besides, many decomposition rules can be attached to a same service with differents guards. 
