package com.sun.corba.se.PortableActivationIDL;


/**
* com/sun/corba/se/PortableActivationIDL/InitialNameServiceOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from t:/workspace/open/src/java.corba/share/classes/com/sun/corba/se/PortableActivationIDL/activation.idl
* Monday, March 26, 2018 at 7:22:23 PM Mountain Daylight Time
*/


/** Interface used to support binding references in the bootstrap name
    * service.
    */
public interface InitialNameServiceOperations 
{

  /** bind initial name
	*/
  void bind (String name, org.omg.CORBA.Object obj, boolean isPersistant) throws com.sun.corba.se.PortableActivationIDL.InitialNameServicePackage.NameAlreadyBound;
} // interface InitialNameServiceOperations
