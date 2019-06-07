# SSPermission

## BDD Scenarios:

**Feature: Permission Request**


**Scenario 1: In Android devices with version 6.0 or higher, Runtime Permission Allowed**

**Given** In Android devices with version 6.0 or higher and Dangerous permission is requested.<br />
**When** Permission request dialog is shown and user can select Allow. <br />
**Then** onGranted method is called with granted permissions.<br />



**Scenario 2: In Android devices with version 6.0 or higher, Runtime Permission Denied**

**Given** In Android devices with version 6.0 or higher and Dangerous permission is requested
**When** Permission request dialog is shown and user can select Deny 
**Then** onDenied method is called with denied permissions


**Scenario 3: In Android devices with version 6.0 or higher, a few Permission Denied , and a few permission allowed**

**Given** In Android devices with version 6.0 or higher and Dangerous permission is requested
**When** Permission request dialog is shown for multiple permissions and user can select Allow for a few and Deny for a few permissions 
**Then** onDenied method is called with denied permissions
**And** onGranted method is called with granted permissions


**Scenario 4: In Android devices with version 6.0 or higher, Runtime Permissions already granted**

**Given** In Android devices with version 6.0 or higher
**When** Already granted Dangerous permission is requested
**Then** Permission request dialog is not shown
**Then** onGranted method is called with all already granted permissions


**Scenario 5: In Android devices with version lower than 6.0, Runtime Permissions is not needed**

**Given** In Android devices with version lower than 6.0
**When** Dangerous Permission is requested
**Then** onGranted method is called with all permissions (as all permission will be granted by the user during installation process)

