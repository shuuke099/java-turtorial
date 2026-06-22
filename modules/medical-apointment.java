Full Hospital ServiceLoader Example

We will build 4 modules:

Module	Job
hospital.appointments.api	Defines the interface
hospital.appointments.clinic	Provides real appointment services
hospital.appointments.booking	Finds services using ServiceLoader
hospital.patient	Uses the system
1. Project Structure
hospital-system/
│
├── hospital.appointments.api/
│   ├── module-info.java
│   └── hospital/appointments/api/AppointmentService.java
│
├── hospital.appointments.clinic/
│   ├── module-info.java
│   └── hospital/appointments/clinic/
│       ├── CardiologyAppointmentService.java
│       ├── DermatologyAppointmentService.java
│       ├── PediatricsAppointmentService.java
│       └── EmergencyFollowUpAppointmentService.java
│
├── hospital.appointments.booking/
│   ├── module-info.java
│   └── hospital/appointments/booking/AppointmentServiceLocator.java
│
└── hospital.patient/
    ├── module-info.java
    └── hospital/patient/PatientApp.java
2. API Module
hospital.appointments.api

This module contains the interface.

AppointmentService.java
package hospital.appointments.api;

public interface AppointmentService {

    String department();

    String bookAppointment(String patientName);
}
module-info.java
module hospital.appointments.api {
    exports hospital.appointments.api;
}
Meaning

This module says:

Any department that wants to book appointments must implement AppointmentService.

3. Provider Module
hospital.appointments.clinic

This module contains the real departments.

Cardiology
package hospital.appointments.clinic;

import hospital.appointments.api.AppointmentService;

public class CardiologyAppointmentService implements AppointmentService {

    @Override
    public String department() {
        return "cardiology";
    }

    @Override
    public String bookAppointment(String patientName) {
        return "Heart appointment booked for " + patientName;
    }
}
Dermatology
package hospital.appointments.clinic;

import hospital.appointments.api.AppointmentService;

public class DermatologyAppointmentService implements AppointmentService {

    @Override
    public String department() {
        return "dermatology";
    }

    @Override
    public String bookAppointment(String patientName) {
        return "Skin appointment booked for " + patientName;
    }
}
Pediatrics
package hospital.appointments.clinic;

import hospital.appointments.api.AppointmentService;

public class PediatricsAppointmentService implements AppointmentService {

    @Override
    public String department() {
        return "pediatrics";
    }

    @Override
    public String bookAppointment(String patientName) {
        return "Child appointment booked for " + patientName;
    }
}
Emergency Follow-up
package hospital.appointments.clinic;

import hospital.appointments.api.AppointmentService;

public class EmergencyFollowUpAppointmentService implements AppointmentService {

    @Override
    public String department() {
        return "emergency";
    }

    @Override
    public String bookAppointment(String patientName) {
        return "Emergency follow-up appointment booked for " + patientName;
    }
}
Provider module-info.java
module hospital.appointments.clinic {
    requires hospital.appointments.api;

    provides hospital.appointments.api.AppointmentService
        with hospital.appointments.clinic.CardiologyAppointmentService,
             hospital.appointments.clinic.DermatologyAppointmentService,
             hospital.appointments.clinic.PediatricsAppointmentService,
             hospital.appointments.clinic.EmergencyFollowUpAppointmentService;
}
Meaning

This module says:

I provide 4 implementations of AppointmentService.

Important syntax:

provides interfaceName with implementationClass;
4. Service Locator Module
hospital.appointments.booking

This module uses ServiceLoader to find the correct department.

AppointmentServiceLocator.java
package hospital.appointments.booking;

import hospital.appointments.api.AppointmentService;

import java.util.ServiceLoader;

public class AppointmentServiceLocator {

    public static AppointmentService findByDepartment(String department) {

        ServiceLoader<AppointmentService> services =
                ServiceLoader.load(AppointmentService.class);

        for (AppointmentService service : services) {
            if (service.department().equalsIgnoreCase(department)) {
                return service;
            }
        }

        throw new RuntimeException("No appointment service found for " + department);
    }
}
module-info.java
module hospital.appointments.booking {
    requires hospital.appointments.api;

    uses hospital.appointments.api.AppointmentService;

    exports hospital.appointments.booking;
}
Meaning

This module says:

I use AppointmentService, and I want Java to find providers at runtime.

Important syntax:

uses interfaceName;
5. Consumer Module
hospital.patient

This is the patient application.

PatientApp.java
package hospital.patient;

import hospital.appointments.api.AppointmentService;
import hospital.appointments.booking.AppointmentServiceLocator;

public class PatientApp {

    public static void main(String[] args) {

        AppointmentService cardio =
                AppointmentServiceLocator.findByDepartment("cardiology");

        System.out.println(cardio.bookAppointment("Amina Hassan"));

        AppointmentService skin =
                AppointmentServiceLocator.findByDepartment("dermatology");

        System.out.println(skin.bookAppointment("Mohamed Ali"));

        AppointmentService child =
                AppointmentServiceLocator.findByDepartment("pediatrics");

        System.out.println(child.bookAppointment("Yusuf Omar"));

        AppointmentService emergency =
                AppointmentServiceLocator.findByDepartment("emergency");

        System.out.println(emergency.bookAppointment("Layla Ahmed"));
    }
}
module-info.java
module hospital.patient {
    requires hospital.appointments.api;
    requires hospital.appointments.booking;
}
Output
Heart appointment booked for Amina Hassan
Skin appointment booked for Mohamed Ali
Child appointment booked for Yusuf Omar
Emergency follow-up appointment booked for Layla Ahmed
How Everything Connects
hospital.patient
        |
        | requires
        v
hospital.appointments.booking
        |
        | uses
        v
AppointmentService
        |
        | provided by
        v
hospital.appointments.clinic
The Most Important Idea

The patient app does not write this:

new CardiologyAppointmentService()

Instead, it writes this:

AppointmentService service =
        AppointmentServiceLocator.findByDepartment("cardiology");

So the consumer knows only the interface, not the real class.

Exam Rules to Remember
Keyword	Used in	Meaning
exports	API module	Makes package visible
requires	Any module	Reads another module
provides ... with ...	Provider module	Registers implementation
uses	Locator/consumer module	Allows ServiceLoader to load services
Easy Memory
API defines the rule.
Provider implements the rule.
Locator finds the provider.
Consumer uses the result.

In this hospital example:

AppointmentService = rule
CardiologyAppointmentService = provider
ServiceLoader = finder
PatientApp = consumer