package com.example;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.LocalMuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
    Map<String, Object> record = new HashMap<String, Object>();

    record.put("PropertyId", "not sure right now");
    record.put("SiteId", "a1Pi0000000Eza4");
    client.dispatch("inboundMessage", record, null);

    MuleMessage message = client.request("reservationUpsert", 10000);
    assertNotNull("No reservationUpsert message received for request", message);
    Map<String,Object> payload = (Map<String, Object>) message.getPayload();
    assertNotNull(payload.get("RoomTypeId"));
  }
}
