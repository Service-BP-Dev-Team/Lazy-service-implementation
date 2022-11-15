# Project setup

Hello! The present repository stores the different project necessary to deploy execute service oriented and component-based applications based on lazy services. The whole repository corresponds to an eclipse workspace constitutes of three projects. By doing so, the easiest way to get started with the project is clone the repository and used it as the workspace of an eclipse application.   

# Notion of lazy/incremental service


Basically, a 'lazy' service is a service that processes lazily its input ( as their need are encountered in the service execution) and return incrementally its outputs (as their computation finish). This implies that a lazy service is capable of producing and output as soon as the subset of the inputs it depends on are available. By doing so, such service are suitable for higher distributive environment where the computation is performed over internet with multiples inputs sources, corresponding themselves as outputs of other ongoing services. Besides they allows to easily combine user activities and machine activities in service execution. In this manner, user skill can be easily automated in a composite lazy service which is obtained by combining automated program and  user interactions. A typical form of a lazy service looks  like :
   
      
      
**ServiceName**( inputs ) < outputs > = {  

var = *ordinaryFunction* ( inputs ) ;   
**subService** ( inputs ) < outputs >;   
varU = *userActivity* ( inputs );   
...  
}
   
      
      
 Stating that the composite service being defined decomposes to a set of ordinary computations (for instance Rest, SOAP, BPEL, Open API services, or or even compiled procedure of a genearal purpose languages such as JAVA, C, C++); a set of sub lazy services and a set of executions implying user activities. From the implementation viewpoint ordinary functions and user activities can be handled by call to features of pre-compiled programms.
 
 # Running Example
 To better explain the behavior of a lazy service, let's play with a small example of course enrollment in a selective computer science school. 
The process starts when a student wants to enroll to certification selection session of the computer science school. In order to register, the student has first to fill in his personal information (name, first name email, etc), a resume and a cover letter that expresses in detail his motivation since the session is selective. This first step can be described by a service :
 
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
