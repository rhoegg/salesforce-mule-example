package com.example;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.LocalMuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;

public class ContactFunctionalTest extends FunctionalTestCase {
  protected final Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  protected String getConfigResources() {
    return "mule-config.xml";
  }

  @BeforeClass
  public static void enableVerboseLogging() {
    System.setProperty("mule.verbose.exceptions", "true");
  }

  @SuppressWarnings("unchecked")
  @Test
  public void shouldUpsertRoomTypeToSalesForce() throws Exception {
    LocalMuleClient client = muleContext.getClient();
    List<Map<String, Object>> records = new ArrayList<Map<String, Object>>();

    Map<String, Object> record = new HashMap<String, Object>();
    record.put("FirstName", "Bugs");
    record.put("LastName", "Bunny");
    records.add(record);

    client.dispatch("inboundMessages", records, null);

    MuleMessage message = client.request("messageDone", 10000);
    assertNotNull("No message received.", message);
    Map<String,Object> payload = (Map<String, Object>) message.getPayload();
    assertNotNull(payload.get("<not sure right now>"));
  }
}
