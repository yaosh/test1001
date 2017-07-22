package com.actions;

import com.service.Humanservice.IHumanservice;

public class ImageTextAction
{
    private IHumanservice humanservice = null;
    
    
    
    public IHumanservice getHumanservice()
    {
        return humanservice;
    }

 

    public void setHumanservice(IHumanservice humanservice)
    {
        this.humanservice = humanservice;
    }



    public String imageMoveToText()
    {
        return "imageMoveToText";
    }
}
