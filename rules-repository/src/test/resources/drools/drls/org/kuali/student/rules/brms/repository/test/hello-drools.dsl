[condition][]Is email "{address}" valid=email : Email( emailAddress != null && emailAddress != "" && emailAddress == "{address}" ) message : Message()
[consequence][]Display message "{message}"=message.setMessage( "{message}" ); message.setValid( true );
