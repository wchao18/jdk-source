package org.omg.PortableInterceptor;


/**
* org/omg/PortableInterceptor/ClientRequestInfo.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from t:/workspace/open/src/java.corba/share/classes/org/omg/PortableInterceptor/Interceptors.idl
* Monday, March 26, 2018 at 7:22:38 PM Mountain Daylight Time
*/


/**
   * Request Information, accessible to client-side request interceptors.
   * <p>
   * Some attributes and operations on <code>ClientRequestInfo</code> are 
   * not valid at all interception points.  The following table shows the 
   * validity of each attribute or operation.  If it is not valid, attempting 
   * to access it will result in a <code>BAD_INV_ORDER</code> being thrown
   * with a standard minor code of 14.
   *
   * <table class="plain">
   * <caption style="display:none">Shows the validity of each attribute or operation</caption>
   *  <thead>
   *    <tr>
   *      <td></td>
   *      <th id="send_req">send_request</th>
   *      <th id="send_poll">send_poll</th>
   *      <th id="rec_reply">receive_reply</th>
   *      <th id="rec_ex">receive_exception</th>
   *      <th id="rec_oth">receive_other</th>
   *    </tr>
   *  </thead>
   *  <tbody style="text-align:left">
   *
   *  <tr>
   *     <th id="ri" colspan=6><i>Inherited from RequestInfo:</i></th>
   *  </tr>
   * 
   *  <tr><th id="req_id">request_id</th>
   *  <td headers="ri req_id send_req">yes</td> 
   *  <td headers="ri req_id send_poll">yes</td> 
   *  <td headers="ri req_id rec_reply">yes</td> 
   *  <td headers="ri req_id rec_ex">yes</td> 
   *  <td headers="ri req_id rec_oth">yes</td></tr>
   * 
   *  <tr><th id="op">operation</th>
   *  <td headers="ri op send_req">yes</td> 
   *  <td headers="ri op send_poll">yes</td> 
   *  <td headers="ri op rec_reply">yes</td> 
   *  <td headers="ri op rec_ex">yes</td> 
   *  <td headers="ri op rec_oth">yes</td></tr>
   *
   *  <tr><th id="arg">arguments</th>
   *  <td headers="ri arg send_req">yes<sub>1</sub></td>
   *  <td headers="ri arg send_poll">no </td> 
   *  <td headers="ri arg rec_reply">yes</td> 
   *  <td headers="ri arg rec_ex">no </td> 
   *  <td headers="ri arg rec_oth">no </td></tr>
   *
   *  <tr><th id="exc">exceptions</th>
   *  <td headers="ri exc send_req">yes</td> 
   *  <td headers="ri exc send_poll">no </td> 
   *  <td headers="ri exc rec_reply">yes</td> 
   *  <td headers="ri exc rec_ex">yes</td> 
   *  <td headers="ri exc rec_oth">yes</td></tr>
   *
   *  <tr><th id="con">contexts</th>
   *  <td headers="ri con send_req">yes</td> 
   *  <td headers="ri con send_poll">no </td> 
   *  <td headers="ri con rec_reply">yes</td> 
   *  <td headers="ri con rec_ex">yes</td> 
   *  <td headers="ri con rec_oth">yes</td></tr>
   *
   *  <tr><th id="op_con">operation_context</th>
   *  <td headers="ri op_con send_req">yes</td> 
   *  <td headers="ri op_con send_poll">no </td> 
   *  <td headers="ri op_con rec_reply">yes</td> 
   *  <td headers="ri op_con rec_ex">yes</td> 
   *  <td headers="ri op_con rec_oth">yes</td></tr>
   *
   *  <tr><th id="result">result</th>
   *  <td headers="ri result send_req">no </td> 
   *  <td headers="ri result send_poll">no </td> 
   *  <td headers="ri result rec_reply">yes</td> 
   *  <td headers="ri result rec_ex">no </td> 
   *  <td headers="ri result rec_oth">no </td></tr>
   *
   *  <tr><th id="res_exp">response_expected</th>
   *  <td headers="ri res_exp send_req">yes</td> 
   *  <td headers="ri res_exp send_poll">yes</td> 
   *  <td headers="ri res_exp rec_reply">yes</td> 
   *  <td headers="ri res_exp rec_ex">yes</td> 
   *  <td headers="ri res_exp rec_oth">yes</td></tr>
   *
   *  <tr><th id="sync_sco">sync_scope</th>
   *  <td headers="ri sync_sco send_req">yes</td> 
   *  <td headers="ri sync_sco send_poll">no </td> 
   *  <td headers="ri sync_sco rec_reply">yes</td> 
   *  <td headers="ri sync_sco rec_ex">yes</td> 
   *  <td headers="ri sync_sco rec_oth">yes</td></tr>
   *
   *  <tr><th id="rep_stat">reply_status</th>
   *  <td headers="ri rep_stat send_req">no </td> 
   *  <td headers="ri rep_stat send_poll">no </td> 
   *  <td headers="ri rep_stat rec_reply">yes</td> 
   *  <td headers="ri rep_stat rec_ex">yes</td> 
   *  <td headers="ri rep_stat rec_oth">yes</td></tr>
   *
   *  <tr><th id="for_ref">forward_reference</th>
   *  <td headers="ri for_ref send_req">no </td>
   *  <td headers="ri for_ref send_poll">no </td> 
   *  <td headers="ri for_ref rec_reply">no </td> 
   *  <td headers="ri for_ref rec_ex">no </td> 
   *  <td headers="ri for_ref rec_oth">yes<sub>2</sub></td></tr>
   *
   *  <tr><th id="get_slot">get_slot</th>
   *  <td headers="ri get_slot send_req">yes</td> 
   *  <td headers="ri get_slot send_poll">yes</td> 
   *  <td headers="ri get_slot rec_reply">yes</td> 
   *  <td headers="ri get_slot rec_ex">yes</td> 
   *  <td headers="ri get_slot rec_oth">yes</td></tr>
   *
   *  <tr><th id="grsc">get_request_service_context</th>
   *  <td headers="ri grsc send_req">yes</td> 
   *  <td headers="ri grsc send_poll">no </td> 
   *  <td headers="ri grsc rec_reply">yes</td> 
   *  <td headers="ri grsc rec_ex">yes</td> 
   *  <td headers="ri grsc rec_oth">yes</td></tr>
   *
   *  <tr><th id="gpsc">get_reply_service_context</th>
   *  <td headers="ri gpsc send_req">no </td> 
   *  <td headers="ri gpsc send_poll">no </td> 
   *  <td headers="ri gpsc rec_reply">yes</td> 
   *  <td headers="ri gpsc rec_ex">yes</td> 
   *  <td headers="ri gpsc rec_oth">yes</td></tr>
   *
   *  <tr>
   *    <th id="cri" colspan=6><i>ClientRequestInfo-specific:</i></th>
   *  </tr>
   *
   *  <tr><th id="target">target</th>
   *  <td headers="cri target send_req">yes</td> 
   *  <td headers="cri target send_poll">yes</td> 
   *  <td headers="cri target rec_reply">yes</td> 
   *  <td headers="cri target rec_ex">yes</td> 
   *  <td headers="cri target rec_oth">yes</td></tr>
   *
   *  <tr><th id="eftarget">effective_target</th>
   *  <td headers="cri eftarget send_req">yes</td> 
   *  <td headers="cri eftarget send_poll">yes</td> 
   *  <td headers="cri eftarget rec_reply">yes</td> 
   *  <td headers="cri eftarget rec_ex">yes</td> 
   *  <td headers="cri eftarget rec_oth">yes</td></tr>
   *
   *  <tr><th id="efprof">effective_profile</th>
   *  <td headers="cri efprof send_req">yes</td> 
   *  <td headers="cri efprof send_poll">yes</td> 
   *  <td headers="cri efprof rec_reply">yes</td> 
   *  <td headers="cri efprof rec_ex">yes</td> 
   *  <td headers="cri efprof rec_oth">yes</td></tr>
   *
   *  <tr><th id="rxp">received_exception</th>
   *  <td headers="cri rxp send_req">no </td> 
   *  <td headers="cri rxp send_poll">no </td> 
   *  <td headers="cri rxp rec_reply">no </td> 
   *  <td headers="cri rxp rec_ex">yes</td> 
   *  <td headers="cri rxp rec_oth">no </td></tr>
   *
   *  <tr><th id="rei">received_exception_id</th>
   *  <td headers="cri rei send_req">no </td> 
   *  <td headers="cri rei send_poll">no </td> 
   *  <td headers="cri rei rec_reply">no </td> 
   *  <td headers="cri rei rec_ex">yes</td> 
   *  <td headers="cri rei rec_oth">no </td></tr>
   *
   *  <tr><th id="gec">get_effective_component</th>
   *  <td headers="cri gec send_req">yes</td> 
   *  <td headers="cri gec send_poll">no </td> 
   *  <td headers="cri gec rec_reply">yes</td> 
   *  <td headers="cri gec rec_ex">yes</td> 
   *  <td headers="cri gec rec_oth">yes</td></tr>
   *
   *  <tr><th id="gecs">get_effective_components</th>
   *  <td headers="cri gecs send_req">yes</td> 
   *  <td headers="cri gecs send_poll">no </td> 
   *  <td headers="cri gecs rec_reply">yes</td> 
   *  <td headers="cri gecs rec_ex">yes</td> 
   *  <td headers="cri gecs rec_oth">yes</td></tr>
   *
   *  <tr><th id="grpcy">get_request_policy</th>
   *  <td headers="cri grpcy send_req">yes</td>
   *  <td headers="cri grpcy send_poll">no </td> 
   *  <td headers="cri grpcy rec_reply">yes</td> 
   *  <td headers="cri grpcy rec_ex">yes</td> 
   *  <td headers="cri grpcy rec_oth">yes</td></tr>
   *
   *  <tr><th id="arsc">add_request_service_context</th>
   *  <td headers="cri arsc send_req">yes</td> 
   *  <td headers="cri arsc send_poll">no </td> 
   *  <td headers="cri arsc rec_reply">no </td> 
   *  <td headers="cri arsc rec_ex">no </td> 
   *  <td headers="cri arsc rec_oth">no </td></tr>
   *
   *   </tbody>
   * </table>
   *
   * <ol>
   *   <li>When <code>ClientRequestInfo</code> is passed to 
   *       <code>send_request</code>, there is an entry in the list for every 
   *       argument, whether in, inout, or out.  But only the in and inout 
   *       arguments will be available.</li>
   *   <li>If the <code>reply_status</code> atribute is not 
   *       <code>LOCATION_FORWARD</code>, accessing this attribute will 
   *       throw <code>BAD_INV_ORDER</code> with a standard minor code of 
   *       14.</li>
   * </ol>
   *
   * @see ClientRequestInterceptor
   */
public interface ClientRequestInfo extends ClientRequestInfoOperations, org.omg.PortableInterceptor.RequestInfo, org.omg.CORBA.portable.IDLEntity 
{
} // interface ClientRequestInfo
