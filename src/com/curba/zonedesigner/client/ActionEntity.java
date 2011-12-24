package com.curba.zonedesigner.client;

import java.util.Date;

public class ActionEntity {
	  private ActionTypeEntity m_actionType;
	  private CropGraphic m_crop;
	  //private $pest;
	  private String m_description;
	  private float m_quantityA;
	  private float m_quantityB;
	  private Date m_createdAt;
	  private Date m_updatedAt;

	  public ActionEntity(ActionTypeEntity type, CropGraphic c, String description, float a, float b) 
	  {
		  m_actionType = type;
		  m_crop = c;
		  m_description = description;
		  m_quantityA = a;
		  m_quantityB = b;
		  m_createdAt = new Date();
		  m_updatedAt = null;
	  }
}