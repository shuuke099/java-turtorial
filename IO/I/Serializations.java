import java.io.*;

class PatientTransferRecord implements Serializable {
    private String patientName;
    private String medicalRecordNumber;
    private String department;

    private transient String temporaryAccessCode;
    private transient boolean reviewed;

    public PatientTransferRecord(String patientName,
                                 String medicalRecordNumber,
                                 String department,
                                 String temporaryAccessCode,
                                 boolean reviewed) {
        this.patientName = patientName;
        this.medicalRecordNumber = medicalRecordNumber;
        this.department = department;
        this.temporaryAccessCode = temporaryAccessCode;
        this.reviewed = reviewed;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        /*
         * Save normal fields:
         * patientName
         * medicalRecordNumber
         * department
         *
         * Do NOT save transient fields:
         * temporaryAccessCode
         * reviewed
         */
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        /*
         * Restore normal saved fields first.
         */
        in.defaultReadObject();

        /*
         * Restore transient fields manually.
         * These were not saved in the file.
         */
        this.temporaryAccessCode = "NEW-CODE-REQUIRED";
        this.reviewed = false;
    }

    @Override
    public String toString() {
        return "PatientTransferRecord{" +
                "patientName='" + patientName + '\'' +
                ", medicalRecordNumber='" + medicalRecordNumber + '\'' +
                ", department='" + department + '\'' +
                ", temporaryAccessCode='" + temporaryAccessCode + '\'' +
                ", reviewed=" + reviewed +
                '}';
    }
}



import java.io.*;

public class HospitalSerializationDemo {
    public static void main(String[] args)
            throws IOException, ClassNotFoundException {

        PatientTransferRecord record =
                new PatientTransferRecord(
                        "Ahmed Ali",
                        "MRN-1001",
                        "ICU",
                        "TEMP-9999",
                        true
                );

        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("transfer-record.ser"))) {
            out.writeObject(record);
        }

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("transfer-record.ser"))) {
            PatientTransferRecord restored =
                    (PatientTransferRecord) in.readObject();

            System.out.println(restored);
        }
    }
}


What happens before serialization?

The object starts like this:

patientName = Ahmed Ali
medicalRecordNumber = MRN-1001
department = ICU
temporaryAccessCode = TEMP-9999
reviewed = true
What gets saved?

Because of:

out.defaultWriteObject();

Java saves normal fields:

patientName = Ahmed Ali
medicalRecordNumber = MRN-1001
department = ICU

But Java does not save transient fields:

temporaryAccessCode
reviewed
What happens after deserialization?

This method runs automatically:

private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException {
    in.defaultReadObject();

    this.temporaryAccessCode = "NEW-CODE-REQUIRED";
    this.reviewed = false;
}

So after reading the object back:

// patientName = Ahmed Ali
// medicalRecordNumber = MRN-1001
// department = ICU
// temporaryAccessCode = NEW-CODE-REQUIRED
// reviewed = false



Why this is useful

// The hospital does not want to restore old temporary security values.
// For example, this old value should not come back:TEMP-9999
// Because it may be expired or unsafe.

// So after deserialization, the system forces:
temporaryAccessCode = NEW-CODE-REQUIRED
reviewed = false

This means:

// The patient transfer record was loaded successfully, but it must be reviewed again before use.

Simple OCP Exam Meaning
Method	Meaning
writeObject()	Runs automatically when saving the object
readObject()	Runs automatically when loading the object
defaultWriteObject()	//Saves normal non-static, non-transient fields
defaultReadObject()	//Restores normal saved fields
transient	Field //is skipped during serialization
Custom logic	//Lets you reset, validate, or rebuild fields
Simple memory rule

// Custom serialization lets you save normal data, skip unsafe temporary data, and rebuild special fields when the object comes back.