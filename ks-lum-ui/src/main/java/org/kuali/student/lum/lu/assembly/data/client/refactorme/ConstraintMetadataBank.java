/*
 * Copyright 2010 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.assembly.data.client.refactorme;


import java.util.HashMap;
import java.util.Map;
import org.kuali.student.core.assembly.data.ConstraintMetadata;


public class ConstraintMetadataBank
{
	public static final Map <String, ConstraintMetadata> BANK = new HashMap ();
	// static initiliazer
	static
	{
		ConstraintMetadata consMeta = null;
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("required");
		consMeta.setMessageId ("kuali.msg.validation.required");
		consMeta.setDesc ("Required");
		consMeta.setMinOccurs (1);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("optional");
		consMeta.setDesc ("Optional");
		consMeta.setComments ("Used to override a previous required constraint.  Also implied by the absence of a required constraint");
		consMeta.setMinOccurs (0);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("not.used");
		consMeta.setMessageId ("kuali.msg.validation.not.used");
		consMeta.setDesc ("Not Used");
		consMeta.setMinOccurs (0);
		consMeta.setMaxOccurs (0);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("repeating");
		consMeta.setDesc ("Repeating");
		consMeta.setComments ("Nine 9's get translated as \"(unbounded)\"");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("single");
		consMeta.setMessageId ("kuali.msg.validation.single");
		consMeta.setDesc ("Single valued (non-repeating)");
		consMeta.setComments ("Used to override a repeating constraint");
		consMeta.setMaxOccurs (1);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hidden");
		consMeta.setDesc ("Hidden (not normally displayed)");
		consMeta.setComments ("Hidden isn't really a constraint but is captured here to help people think aobut the data and eventualy to help feed the configurable UI.");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("read.only");
		consMeta.setDesc ("Read Only");
		consMeta.setComments ("Read-only isn't really a constraint on the data but is a constraint on the UI. It is here so it can be easily flagged as an attribute so thait can eventually feed the UI.");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("code");
		consMeta.setMessageId ("kuali.msg.validation.code");
		consMeta.setDesc ("Alphanumeric code, hypen and period.");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		consMeta.setValidChars ("regex:[A-Za-z0-9.-]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("code.uppercase");
		consMeta.setMessageId ("kuali.msg.validation.uppercase");
		consMeta.setDesc ("uppercase alphanumeric code, hypen and period.");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		consMeta.setValidChars ("regex:[A-Z0-9.-]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("code.lowercase");
		consMeta.setMessageId ("kuali.msg.validation.lowercase");
		consMeta.setDesc ("lowercase alphanumeric code, hypen and period.");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		consMeta.setValidChars ("regex:[a-z0-9.-]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("single.line.text");
		consMeta.setMessageId ("kuali.msg.validation.single.line.text");
		consMeta.setDesc ("A basic single line of text, no embedded carraige returns or tabs limited to 255");
		consMeta.setComments ("old reg ex was \"[A-Za-z0-9.-;;'&%$#@!]*");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (255);
		consMeta.setValidChars ("regex:[A-Za-z0-9.-;:\"'&%$#@!\\t ]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("letter");
		consMeta.setMessageId ("kuali.msg.validation.letter");
		consMeta.setDesc ("letters only");
		consMeta.setValidChars ("regex:[A-Za-z]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("size.one");
		consMeta.setMessageId ("kuali.msg.validation.size.one");
		consMeta.setDesc ("Only one character");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (1);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("size.two");
		consMeta.setMessageId ("kuali.msg.validation.size.two");
		consMeta.setDesc ("Two characters both must be filled out.");
		consMeta.setMinLength (2);
		consMeta.setMaxLength (2);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("size.up.to.two");
		consMeta.setMessageId ("kuali.msg.validation.size.up.to.two");
		consMeta.setDesc ("Up to 2 characters long");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (2);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("no.linefeeds");
		consMeta.setMessageId ("kuali.msg.validation.no.linefeeds");
		consMeta.setDesc ("Any character EXCEPT carraige returns and line feeds");
		consMeta.setValidChars ("regex:[^\\n\\r]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("no.tabs");
		consMeta.setMessageId ("kuali.msg.validation.no.tabs");
		consMeta.setDesc ("Any character EXCEPT a tab");
		consMeta.setValidChars ("regex:[^\\t]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("multi.line.text");
		consMeta.setMessageId ("kuali.msg.validation.printable");
		consMeta.setDesc ("Multi-line text field that accepts all printable characters plus tab, carraige-return and linefeed.");
		consMeta.setComments ("old reg ex was [A-Za-z0-9.-;;'&%$#@!\\n\\r\\t]*");
		consMeta.setMinLength (1);
		consMeta.setValidChars ("regex:[A-Za-z0-9.-;:\"'&%$#@!\\n\\r\\t ]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("rich.text");
		consMeta.setMessageId ("kuali.msg.validation.printable");
		consMeta.setDesc ("Text field that accepts all types of characters using some sort of escaping convention along with the ability to specify bolding, font size, color etc.");
		consMeta.setComments ("Not sure if characterset for rich text is the same for regular text but just interpretted differently.  I.e. html is plain text interpreted differently.");
		consMeta.setMinLength (1);
		consMeta.setValidChars ("regex:[A-Za-z0-9.-;:\"'&%$#@!\\n\\r\\t ]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("date");
		consMeta.setMessageId ("kuali.msg.validation.date");
		consMeta.setDesc ("Date");
		consMeta.setComments ("http://regexlib.com/REDetails.aspx?regexp_id=760 (but not sure)");
		consMeta.setMinLength (10);
		consMeta.setMaxLength (10);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("date.time");
		consMeta.setMessageId ("kuali.msg.validation.date.time");
		consMeta.setDesc ("Date-Time");
		consMeta.setComments ("http://regexlib.com/REDetails.aspx?regexp_id=1966 (but not sure)");
		consMeta.setMinLength (19);
		consMeta.setMaxLength (19);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("integer");
		consMeta.setMessageId ("kuali.msg.validation.integer");
		consMeta.setDesc ("Integer");
		consMeta.setComments ("http://regexlib.com/REDetails.aspx?regexp_id=14");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (10);
		consMeta.setValidChars ("regex:^(\\+|-)?[0-9]+$");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("non-negative.integer");
		consMeta.setMessageId ("kuali.msg.validation.non-negative.integer");
		consMeta.setDesc ("Non-Negative Integer");
		consMeta.setComments ("http://regexlib.com/REDetails.aspx?regexp_id=13");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (10);
		consMeta.setMinValue ("0");
		consMeta.setValidChars ("regex:^[0-9]+$");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("decimal");
		consMeta.setMessageId ("kuali.msg.validation.decimal");
		consMeta.setDesc ("decimal number");
		consMeta.setComments ("http://regexlib.com/REDetails.aspx?regexp_id=117");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (10);
		consMeta.setValidChars ("regex:^[-+]?[0-9]+(\\.[0-9]+)?$");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("non.negative.decimal");
		consMeta.setMessageId ("kuali.msg.validation.non-negative.decimal");
		consMeta.setDesc ("non-negative decimal number");
		consMeta.setComments ("http://regexlib.com/REDetails.aspx?regexp_id=117");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (10);
		consMeta.setValidChars ("regex:^[-+]?[0-9]+(\\.[0-9]+)?$");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("amount");
		consMeta.setMessageId ("kuali.msg.validation.amount");
		consMeta.setDesc ("Amount field");
		consMeta.setComments ("http://regexlib.com/REDetails.aspx?regexp_id=131");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (10);
		consMeta.setValidChars ("regex:^\\$?[0-9]+(\\.([0-9]{2}))?$");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("url");
		consMeta.setMessageId ("kuali.msg.validation.url");
		consMeta.setDesc ("Uniform Resource Locator");
		consMeta.setComments ("http://regexlib.com/REDetails.aspx?regexp_id=96");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (2083);
		consMeta.setValidChars ("regex:(http|ftp|https):\\/\\/[A-Za-z0-9.-]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("boolean");
		consMeta.setMessageId ("kuali.msg.validation.boolean");
		consMeta.setDesc ("True or False");
		consMeta.setComments ("http://regexlib.com/REDetails.aspx?regexp_id=905");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (5);
		consMeta.setValidChars ("regex:[Ff]+(alse)?|[Tt]+(rue)");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("kuali.id");
		consMeta.setMessageId ("kuali.msg.validation.kuali.id");
		consMeta.setDesc ("Kuali ID; calculated by service on add, then read-only.");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (36);
		consMeta.setMaxOccurs (1);
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.validation.KualiIdValidator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("kuali.key");
		consMeta.setMessageId ("kuali.msg.validation.kuali.key");
		consMeta.setDesc ("Kuali KEY; structured like a type but unique like and ID, used to identify configured objects.");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		consMeta.setMinOccurs (1);
		consMeta.setMaxOccurs (1);
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.validation.KualiTypeValidator");
		consMeta.setValidChars ("regex:[A-Za-z0-9.-]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("kuali.type");
		consMeta.setMessageId ("kuali.msg.validation.kuali.type");
		consMeta.setDesc ("Kuali TYPE: required on add, then read-only");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		consMeta.setMinOccurs (1);
		consMeta.setMaxOccurs (1);
		consMeta.setServerSide (true);
		consMeta.setValidChars ("regex:[A-Za-z0-9.-]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("kuali.state");
		consMeta.setMessageId ("kuali.msg.validation.kuali.state");
		consMeta.setDesc ("Kuali STATE");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (30);
		consMeta.setMinOccurs (1);
		consMeta.setMaxOccurs (1);
		consMeta.setServerSide (true);
		consMeta.setValidChars ("regex:[A-Za-z0-9.-]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("kuali.meta.data");
		consMeta.setMessageId ("kuali.msg.validation.kuali.meta.data");
		consMeta.setDesc ("Kuali Meta Data: calculated by service, read-only");
		consMeta.setMaxOccurs (1);
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.validation.KualiMetaDataValidator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("related.cluId");
		consMeta.setMessageId ("kuali.msg.validation.not.a.valid.related.id");
		consMeta.setDesc ("Id of an existing CLU");
		consMeta.setComments ("Requires special logic to check that the ID supplied actually exists");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (36);
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.lum.lu.validation.CluIdExistsValidator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("related.clu.set.id");
		consMeta.setMessageId ("kuali.msg.validation.not.a.valid.related.id");
		consMeta.setDesc ("Id of an existing CLU Set Id");
		consMeta.setComments ("Requires special logic to check that the ID supplied actually exists");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (36);
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.lum.lu.validation.CluSetIdExistsValidator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("related.orgId");
		consMeta.setMessageId ("kuali.msg.validation.not.a.valid.related.id");
		consMeta.setDesc ("Id of an existing ORG");
		consMeta.setComments ("Requires special logic to check that the ID supplied actually exists");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (36);
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.org.validation.OrgIdExistsValidator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("related.principalId");
		consMeta.setMessageId ("kuali.msg.validation.not.a.valid.related.id");
		consMeta.setDesc ("Id of an existing PRINCIPAL");
		consMeta.setComments ("Requires special logic to check that the ID supplied actually exists");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (36);
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.auth.validation.PrincipaldExistsValidator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("related.personId");
		consMeta.setMessageId ("kuali.msg.validation.not.a.valid.related.id");
		consMeta.setDesc ("Id of an existing PERSON");
		consMeta.setComments ("Requires special logic to check that the ID supplied actually exists");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (36);
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.person.validation.PersonIddExistsValidator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("related.resultValueId");
		consMeta.setMessageId ("kuali.msg.validation.not.a.valid.related.id");
		consMeta.setDesc ("Id of an existing CREDIT, CREDENTIAL, or GRADE");
		consMeta.setComments ("Requires special logic to check that the Id supplied actuall exists");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.lum.lrc.validation.ResultValueObjectExistsValidator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("related.resultComponentId");
		consMeta.setMessageId ("kuali.msg.validation.not.a.valid.related.id");
		consMeta.setDesc ("Id of an exisitn resultComponent");
		consMeta.setComments ("Requires special logic to check that the Id supplied actuall exists");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.lum.lrc.validation.ResultComponentExistsValidator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("related.gradekey");
		consMeta.setMessageId ("kuali.msg.validation.not.a.valid.related.id");
		consMeta.setDesc ("Key of an existing Grade");
		consMeta.setComments ("Requires special logic to check that the key supplied actuall exists");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.lum.lrc.validation.GradeExistsValidator");
		consMeta.setValidChars ("regex:[A-Za-z0-9.-]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("related.creditkey");
		consMeta.setMessageId ("kuali.msg.validation.not.a.valid.related.id");
		consMeta.setDesc ("Key of an existing Credit");
		consMeta.setComments ("Requires special logic to check that the key supplied actuall exists");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.lum.lrc.validation.CreditExistsValidator");
		consMeta.setValidChars ("regex:[A-Za-z0-9.-]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("related.credentialkey");
		consMeta.setMessageId ("kuali.msg.validation.not.a.valid.related.id");
		consMeta.setDesc ("Key of an existin Credential");
		consMeta.setComments ("Requires special logic to check that the key supplied actuall exists");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.lum.lrc.validation.credentialExistsValidator");
		consMeta.setValidChars ("regex:[A-Za-z0-9.-]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("related.proposal");
		consMeta.setMessageId ("kuali.msg.validation.not.a.valid.related.id");
		consMeta.setDesc ("Id of an existing PROPOSAL");
		consMeta.setComments ("Requires special logic to check that the ID supplied actually exists");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (36);
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.proposal.validation.ProposalIdExistsValidator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("related.document");
		consMeta.setMessageId ("kuali.msg.validation.not.a.valid.related.id");
		consMeta.setDesc ("Id of an existing DOCUMENT");
		consMeta.setComments ("Requires special logic to check that the ID supplied actually exists");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (36);
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.document.validation.DocumentIdExistsValidator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lu.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured learning unit types");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lu.identifier.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured clu identifier types");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lu.activity.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In a list of configured activity LuTypes");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("duration.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured duration types");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("instructional.format.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured instructional formats");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (30);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("season.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured seasons");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("atp.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured  time period types");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("milestone.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured  time period milestone types");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("daterange.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured  time period daterange types");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("dynamic.field.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured dynamic field types");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lu.lo.relation.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured lu-lo relation types");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lu.lu.relation.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured lul-lu relation types");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lu.code.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured classification codes used for LU");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("currency.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured currency types");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lo.category.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of lo category types");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lo.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of confured lo types");
		consMeta.setComments ("TO DO: add to SimpleS");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lo.lo.relation.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured lo-lo relationship types");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lo.repositories");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured lo repositories");
		consMeta.setComments ("TO DO: add to SimpleS");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("cluResult.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured cluResultTypes");
		consMeta.setComments ("TO DO: add to SimpleS");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("resultUsage.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of confured resultUsage types");
		consMeta.setComments ("TO DO: add to Simple Spec");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("proposal.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In a list of configured proposal types");
		consMeta.setComments ("TO DO: add to SimpleS");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("course.proposal.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In a list of configured course proposal types");
		consMeta.setComments ("TO DO: add to SimpleS");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("program.proposal.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In a list of configured program proposal types");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lo.hierarchy.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In a list of configured program lo hierarchy types");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("rate.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In a list of configured rate types");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("reference.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In a list of configured reference types");
		consMeta.setComments ("TO DO: add to SimpleS");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (60);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lu.states");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured learning unit states");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (30);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lu.identifier.states");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured clu identifier states");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (30);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("subject.areas");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured subject areas");
		consMeta.setMinLength (4);
		consMeta.setMaxLength (4);
		consMeta.setValidChars ("regex:[A-Z]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("available.course.numbers");
		consMeta.setMessageId ("kuali.msg.validation.not.an.enumerated.list");
		consMeta.setDesc ("In list of configured available course numbers");
		consMeta.setMinLength (3);
		consMeta.setMaxLength (3);
		consMeta.setValidChars ("regex:[0-9]*");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("offering.campuses");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured offering campuses");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (30);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("unit.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured unit types");
		consMeta.setComments ("Although called a type, this is not a formal type in kuali student like an lu type, for example it may be changeable after being added.");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (10);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("eye.colors");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In a list of configured eye colors");
		consMeta.setComments ("Blue, Brown, Green, Hazel, etc");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (10);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("grade.scales");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In a list of configured grading scales");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("effective.date.atps");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In a list of configured ATPs");
		consMeta.setComments ("Not entirely sure what to call this. There is also a kuali.enum.type.EffectiveDateATPs defined for the which is backed by this enumeration (its further constrained) but I don't know that it needs to be represented that way.");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.type.credit.course");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.type.CreditCourse\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.lu\\.type\\.CreditCourse");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.type.credit.course.format.shell");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.type.CreditCourseFormatShell\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.lu\\.type\\.CreditCourseFormatShell");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.type.program");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.type.Program\"");
		consMeta.setServerSide (true);
		consMeta.setValidChars ("regex:kuali\\.lu\\.type\\.Program");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.type.program.level");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.type.ProgramLevel\"");
		consMeta.setServerSide (true);
		consMeta.setValidChars ("regex:kuali\\.lu\\.type\\.ProgramLevel");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.type.program.type");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.type.ProgramType\"");
		consMeta.setServerSide (true);
		consMeta.setValidChars ("regex:kuali\\.lu\\.type\\.ProgramType");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.type.degree.level");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.type.DegreeLevel\"");
		consMeta.setServerSide (true);
		consMeta.setValidChars ("regex:kuali\\.lu\\.type\\.DegreeLevel");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.type.degree.type");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.type.DegreeType\"");
		consMeta.setServerSide (true);
		consMeta.setValidChars ("regex:kuali\\.lu\\.type\\.DegreeType");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.type.major");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.type.Major\"");
		consMeta.setServerSide (true);
		consMeta.setValidChars ("regex:kuali\\.lu\\.type\\.Major");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.type.minor");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.type.Minor\"");
		consMeta.setServerSide (true);
		consMeta.setValidChars ("regex:kuali\\.lu\\.type\\.Minor");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.type.general.ed");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.type.GeneralEd\"");
		consMeta.setServerSide (true);
		consMeta.setValidChars ("regex:kuali\\.lu\\.type\\.GeneralEd");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.type.honors");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.type.Honors\"");
		consMeta.setServerSide (true);
		consMeta.setValidChars ("regex:kuali\\.lu\\.type\\.Honors");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.official");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.type.CreditCourse.identifier.official\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.lu\\.type\\.CreditCourse\\.identifier\\.official");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.active");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"active\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:active");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.cross-listed");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.type.CreditCourse.identifier.cross-listed\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.lu\\.type\\.CreditCourse\\.identifier\\.cross-listed");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.co-located");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.relation.type.co-located\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.lu\\.relation\\.type\\.co-located");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.lulurelation.courseformat");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.lu.relation.type.hasCourseFormat\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kulai\\.lu\\.lu\\.relation\\.type\\.hasCourseFormat");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.lulurelation.programlevel");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.lu.relation.type.hasProgramLevel\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.lu\\.lu\\.relation\\.type\\.hasProgramLevel");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.lulurelation.programtype");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.lu.relation.type.hasProgramType\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.lu\\.lu\\.relation\\.type\\.hasProgramType");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.version");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.type.CreditCourse.identifier.version\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.lu\\.type\\.CreditCourse\\.identifier\\.version");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.zero");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be 0");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:0");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.one");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be 1");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:1");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.true");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be TRUE");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:true");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.false");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be FALSE");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:false");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.eye.color");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.type.CreditCourse.dynamic.eye.color\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.lu\\.type\\.CreditCourse\\.dynamic\\.eye\\.color");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.left.handed");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.type.CreditCourse.dynamic.left.handed\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.lu\\.type\\.CreditCourse\\.dynamic\\.left\\.handed");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.rate.type");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.type.CreditCourse.dynamic.rate.type\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.lu\\.type\\.CreditCourse\\.dynamic\\.rate\\.type");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.singleUse.lo");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("must be \"kuali.lo.type.singleUse\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.lo\\.type\\.singleUse");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.singleUse.lo.sequence");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("must be \"kuali.lo.type.SingleUse.dynamic.sequence\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.lo\\.type\\.singleUse\\.dynamic\\.sequence");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.unclassified.lo.category");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("must be \"kuali.lo.category.type.unclassified\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.lo\\.category\\.type\\.unclassified");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.accreditation.lo.category");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("must be \"kuali.lo.category.type.accreditation\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.lo\\.category\\.type\\.accreditation");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.skill.lo.category");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("must be \"kuali.lo.category.type.skill\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.lo\\.category\\.type\\.skill");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.subject.lo.category");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("must be \"kuali.lo.category.type.subject\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.lo\\.category\\.type\\.subject");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.lulorealtion.includes");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lu.lo.relation.type.includes\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kulai\\.lu\\.lo\\.relation\\.type\\.includes");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.lolorealtion.includes");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.lo.lo.relation.type.includes\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kulai\\.lo\\.lo\\.relation\\.type\\.includes");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.singleUse.lo.repository");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.loRepository.key.single.use\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.loRepository\\.key\\.singleUse");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.credit.awarded.resultUsage");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("must be \"Kuali.resultUsageType.CreditAwarded\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:Kuali\\.resultUsageType\\.CreditAwarded");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.fixed.credit");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.resultComponentType.credit.degree.fixed\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.resultComponentType\\.credit\\.degree\\.fixed");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.variable.credit");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.resultComponentType.credit.degree.range\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.resultComponentType\\.credit\\.degree\\.range");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.final.grade");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be\"kuali.resultComponentType.finalGrade\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.resultComponentType\\.finalGrade");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.academic.credit");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.creditType.credit.degree\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.creditType\\.credit\\.degree");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.final.qualitative.result.Usage");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.resultComponentType.finalQualitative\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.resultComponentType\\.finalQualitative");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.final.grad.awarded.resultUsage");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"Must be \"kuali.resultUsageType.finalGradeAwarded\"\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.resultUsageType\\.finalGradeAwarded");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.letter.grade");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.gradeType.Letter\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.gradeType\\.Letter");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.pass-fail.grade");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.gradeType.Pass-Fail\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.gradeType\\.Pass-Fail");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.final.qualitative");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be\"kuali.resultComponentType.finalQualitative\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.resultComponentType\\.finalQualitative");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.referenceType.CLU");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.referenceType.CLU\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.referenceType\\.CLU");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.referenceType.Proposal");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.referenceType.proposal\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.referenceType\\.proposal");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.group.proposal");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"kuali.proposal.type.group\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.proposal\\.type\\.group");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.syllabus");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be\"kuali.proposal.ProposalDocRelationType.syllabus\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.proposal\\.ProposalDocRelationType\\.syllabus");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.other.document");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be\"kuali.proposal.ProposalDocRelationType.other\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.proposal\\.ProposalDocRelationType\\.other");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.file");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be\"kuali.document.type.file\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.document\\.type\\.file");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.course.proposal.doc.category");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be\"kuali.document.category.courseProposal.Doc.Category\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.document\\.category\\.courseProposal\\.Doc\\.Category");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.rateType.fixedRate");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"Fixed Rate\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:Fixed Rate");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.rateType.variableRate");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"Variable Rate\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:Variable Rate");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.rateType.multipleRate");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"Multiple Rate\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:Multiple Rate");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.rateType.perCreditRate");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"Per Credit Rate\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:Per Credit Rate");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.feeType.Revenue");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be \"Revenue\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:Revenue");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("calc.course.no");
		consMeta.setDesc ("Calculates the course number from it's various parts by concatenating them together. For KRU this calculation is: code = division + suffixCode + version");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.lum.lu.calculation.CourseNoCalculator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("calc.copy.course.official.subjectArea");
		consMeta.setDesc ("Same value as the value of the course.official.subjectArea field");
		consMeta.setComments ("Not sure how to implement this copy operation or invoke it every time the official course no gets updated");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.lum.lu.calculation.CopyOfficialCourseSubjectAreaCalculator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("calc.copy.course.official.numberPart");
		consMeta.setDesc ("Same value as the value of the course.official.numberPart field");
		consMeta.setComments ("Not sure how to implement this copy operation or invoke it every time the official course no gets updated");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.lum.lu.calculation.CopyOfficialCourseNumberPartCalculator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("calc.program.no");
		consMeta.setDesc ("Not sure of the form for program just yet");
		consMeta.setComments ("Don't know all the details yet");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.lum.lu.calculation.ProgramNoCalculator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("calc.meta.versionInd");
		consMeta.setDesc ("Calculates the version indicator that is used for optimistic locking");
		consMeta.setComments ("This could be as simple as a nano-second date-time stamp of the last update or a sequence number of the object that gets incremented on each update or some other more complex computation.");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculationVersionIndCalculator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("calc.now");
		consMeta.setDesc ("Sets a field to the current time");
		consMeta.setComments ("Worry about timezone issues");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.NowCalculator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("calc.today");
		consMeta.setDesc ("Sets a field to system date");
		consMeta.setComments ("Worry about timezone issues");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.TodayCalculator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("calc.authenicated.principalId");
		consMeta.setDesc ("Sets a field to the currently authenticated user");
		consMeta.setComments ("How do we know who the real user is when a service calls another service?");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.auth.calculation.AuthenticatedPrincipalCalculator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("calc.plain.text");
		consMeta.setDesc ("Strips the formatting out of the formatted version of the rich text field and stores it in this plain text field");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.PlainTextCalculator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("has.course.format.relation.lu.types");
		consMeta.setMessageId ("kuali.msg.validation.type.not.valid.for.type");
		consMeta.setDesc ("constrains the required LuType of the clu referenced by cluCluRelationInfo.cluID for a Has Course Format relation");
		consMeta.setComments ("TO DO: add the enumeration to simples");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("is.course.format.relation.lu.types");
		consMeta.setMessageId ("kuali.msg.validation.type.not.valid.for.type");
		consMeta.setDesc ("constrains the required LuType of clu referenced by cluCluRelationInfo.relatedCluId for a has course format relation");
		consMeta.setComments ("TO DO: add the enumeration to simples");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("contains.relation.lu.types");
		consMeta.setMessageId ("kuali.msg.validation.type.not.valid.for.type");
		consMeta.setDesc ("constrains the required LuType of the clu referenced by cluCluRelationInfo.cluID for a \"Contains\" relation");
		consMeta.setComments ("TO DO: add the enumeration to simples");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("contained.by.relation.lu.types");
		consMeta.setMessageId ("kuali.msg.validation.type.not.valid.for.type");
		consMeta.setDesc ("constrains the required LuType of the clu referenced by cluCluRelationInfo.relatedCluID for a \"Contain\" relation");
		consMeta.setComments ("TO DO: add the enumeration to simples");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("valid.duration.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In a list of duration types configured by LuType");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("valid.atp.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In a list of atp types configured by LuType");
		consMeta.setComments ("TO DO: add this enumeration to the simples");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("cross.greaterthan.effective.date");
		consMeta.setMessageId ("kuali.msg.validation.date.less.than.effective");
		consMeta.setDesc ("Must be empty or a date greater than the entity's effective date");
		consMeta.setComments ("Not sure if this is how it should be handled - a calc seems like it should be server side, but we don't want this to be server side. In general, it is not clear how to document cross-field constraints");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("default.now");
		consMeta.setDesc ("Defaults to NOW, when the object is created");
		consMeta.setComments ("Not sure how to implement defaults");
		consMeta.setServerSide (true);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("default.today");
		consMeta.setDesc ("Defaults to TODAY, when the object is created");
		consMeta.setComments ("Not sure how to implement defaults");
		consMeta.setServerSide (true);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("default.true");
		consMeta.setDesc ("Defaults a boolean to TRUE");
		consMeta.setComments ("Not sure how to implement defaults");
		consMeta.setServerSide (true);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("default.active");
		consMeta.setDesc ("Defaults to \"active\"");
		consMeta.setComments ("Not sure how to implement defaults");
		consMeta.setServerSide (true);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("default.false");
		consMeta.setDesc ("Defaults a boolean to FALSE");
		consMeta.setComments ("Not sure how to implement defaults");
		consMeta.setServerSide (true);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("default.zero");
		consMeta.setDesc ("Defaults number to zero, 0");
		consMeta.setComments ("May be applied to strings that hold numbers as well as numeric fields proper");
		consMeta.setServerSide (true);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("default.one");
		consMeta.setDesc ("Defaults number to a one, 1");
		consMeta.setComments ("May be applied to strings that hold numbers as well as numeric fields proper");
		consMeta.setServerSide (true);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("default.authenicated.principalId");
		consMeta.setDesc ("Defaults to the currently authenticated user");
		consMeta.setComments ("How do we know who the real user is when a service calls another service?");
		consMeta.setServerSide (true);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("default.to.proposal.proposerOrg");
		consMeta.setDesc ("Defaults to the value to the proposer org from the related prposal");
		consMeta.setComments ("Not sure how to implement this");
		consMeta.setServerSide (true);
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("statement.operators");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured statement operators (AND and OR)");
		consMeta.setComments ("Hard coded to be AND or OR");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("statement.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of valid statement types");
		consMeta.setComments ("See LuStatementInfo types defined on the Types tab");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("statement.states");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of valid statement states");
		consMeta.setComments ("See LuStatementInfo states defined on the States tab");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("related.Statementd");
		consMeta.setMessageId ("kuali.msg.validation.not.a.valid.related.id");
		consMeta.setDesc ("Id of an existing Statement");
		consMeta.setComments ("Requires special logic to check that the ID supplied actually exists");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (36);
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.lum.lu.validation.LuStatementIdExistsValidator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("related.requirementComponentId");
		consMeta.setMessageId ("kuali.msg.validation.not.a.valid.related.id");
		consMeta.setDesc ("Id of an existing requirement component");
		consMeta.setComments ("Requires special logic to check that the ID supplied actually exists");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (36);
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.lum.lu.validation.RequirementComponentIdExistsValidator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lu.requirement.component.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of valid lu requirement component types");
		consMeta.setComments ("See reqComponentInfo types defined on the Types tab");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("dictionary.field.descriptor.field.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of valid data types fof fields that are supported by the validator");
		consMeta.setComments ("Valid values are: complex, string, date, dateTime, boolean, integer, & long");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("dictionary.enumeration.keys");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of the keys for all valid enumerations known to the system");
		consMeta.setComments ("See the lookup column of this spreadsheet!!!!!");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("dictionary.enumeration.context.keys");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of the keys for all valid enumeration contexts known to the system");
		consMeta.setComments ("See the lookupContext column of this spreadsheet!!!!!");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lu.requirement.component.states");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of valid lu requirement component states");
		consMeta.setComments ("See reqComponentInfo states defined on the States tab");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lu.requirement.component.field.keys");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of valid lu requirement component field keys");
		consMeta.setComments ("See the reqCompFieldTypeInfo keys defined on the Types tab");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("search.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of valid search types");
		consMeta.setComments ("See all the searches defined in the simple specs");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("query.param.value.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of valid query parameter types for searches");
		consMeta.setComments ("See all the searches defined in the simple specs");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lrd.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of valid learning result definition types");
		consMeta.setComments ("See the lrd types defined in this spreadsheet");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("learning.result.scale.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of valid learning result scale types");
		consMeta.setComments ("See the scale types defined in this spreadsheet");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("lrd.operators");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of configured learning result definition operators (=, >, < etc)");
		consMeta.setComments ("Hard coded to be equal_to, not_equal_to, greater_than, less_than, greater_than_or_equal_to, less_than_or_equal_to");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("related.lrd.Id");
		consMeta.setMessageId ("kuali.msg.validation.not.a.valid.related.id");
		consMeta.setDesc ("Id of an existing learning result definition");
		consMeta.setComments ("Requires special logic to check that the ID supplied actually exists");
		consMeta.setMinLength (1);
		consMeta.setMaxLength (36);
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.lum.lrd.validation.LearningResultDefinitionIdExistsValidator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.credit.course.clu");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be\"kuali.reqCompFieldType.credit.course\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.reqCompFieldType\\.credit\\.course");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.credit.course.clu.set");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be\"kuali.reqCompFieldType.credit.course.set\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.reqCompFieldType\\.credit\\.course\\.set");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.required.count");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be\"kuali.reqCompFieldType.requiredCount\"");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.reqCompFieldType\\.requiredCount");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.lrd.credit.course.final.grade");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be\"kuali.reqCompFieldType.");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.reqCompFieldType\\.lrd\\.credit\\.course\\.final\\.grade");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.overall.gpa");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be\"kuali.reqCompFieldType.");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.reqCompFieldType\\.overall\\.gpa");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.overall.totalCredits");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be\"kuali.reqCompFieldType.");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.reqCompFieldType\\.overall\\.totalCredits");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.operator");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be\"kuali.reqCompFieldType.");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.reqCompFieldType\\.operator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("atp.in.future");
		consMeta.setMessageId ("kuali.msg.validation.atp.not.in.future");
		consMeta.setDesc ("The ATP must start in the future");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.atp.validation.FutureATPValidatorValidator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("course.variation.code.unique");
		consMeta.setMessageId ("kuali.msg.validation.course.variation.not.unique");
		consMeta.setDesc ("The variation codes must be unique");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.lum.lu.validation.UniqueVariationValidatorValidator");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("hard.coded.countType");
		consMeta.setMessageId ("kuali.msg.validation.hard.coded");
		consMeta.setDesc ("Must be\"kuali.reqCompFieldType.");
		consMeta.setServerSide (true);
		consMeta.setSpecialValidator ("org.kuali.student.core.calculation.HardCodedValueCalculator");
		consMeta.setValidChars ("regex:kuali\\.reqCompFieldType\\.countType");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("clu.set.types");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of valid clu set types");
		consMeta.setComments ("See cluSetInfo states defined on the Types tab");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("clu.set.states");
		consMeta.setMessageId ("kuali.msg.validation.not.in.enumerated.list");
		consMeta.setDesc ("In list of valid clu set states");
		consMeta.setComments ("See cluSetInfo states defined on the States tab");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
		
		consMeta = new ConstraintMetadata ();
		consMeta.setId ("valid.credit.course.clu.set.search.types");
		consMeta.setMessageId ("kuali.msg.validation.type.not.valid.for.type");
		consMeta.setDesc ("In list of search times that return credit courses or clu sets of credit courses");
		consMeta.setComments ("Has just one value for now and that is \"kuali.lu.searchTypes.CoursesForDynamicCLUSet\" but that will expand in the future.");
		BANK.put (consMeta.getId ().toLowerCase (), consMeta);
	}
}

