Ext.application({
    name : 'Fiddle',

    launch : function() {
        
    Ext.create('Ext.Panel', {
    width: 500,
    height: 400,
    title: "Profile Settings",
    layout: 'form',
    renderTo: Ext.getBody(),
    bodyPadding: 5,
    defaultType: 'textfield',
    items: [{
       fieldLabel: 'First Name',
        name: 'first',
        allowBlank:false
    },{
        fieldLabel: 'Last Name',
        name: 'last'
    },
            
    {
            xtype      : 'fieldcontainer',
            fieldLabel : 'Sex',
            defaultType: 'radiofield',
            defaults: {
                flex: 1
            },
            layout: 'vbox',
            items: [
                {
                    boxLabel  : 'Male',
                    name      : 'size',
                    inputValue: 'm',
                    id        : 'radio1'
                }, {
                    boxLabel  : 'Female',
                    name      : 'size',
                    inputValue: 'f',
                    id        : 'radio2'
                }
            ]
        },        
            
            
            
    {
        fieldLabel: 'Team',
        name: 'team'
    }, {
        fieldLabel: 'Email',
        name: 'email',
        vtype:'email'
    }, {
        fieldLabel: 'HireDate',
        name: 'hiredate',
        xtype: 'datefield'
    }
     
           
   ],
    
    // Reset and Submit buttons
    buttons: [{
        text: 'Reset',
        handler: function() {
            this.up('form').getForm().reset();
        }
    }, {
        text: 'Submit',
        formBind: true, //only enabled once the form is valid
        disabled: true,
        handler: function() {
            var form = this.up('form').getForm();
            if (form.isValid()) {
                form.submit({
                    success: function(form, action) {
                       Ext.Msg.alert('Success', action.result.msg);
                    },
                    failure: function(form, action) {
                        Ext.Msg.alert('Failed', action.result.msg);
                    }
                });
            }
        }
    }]
            
});
    }
});
