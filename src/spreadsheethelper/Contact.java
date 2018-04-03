package spreadsheethelper;

public class Contact {
    private String name;
    private String contact;
    private String email;

    public Contact(String name, String contact) {
        this.name = name;
        this.contact = contact.toLowerCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Contact && ((Contact) obj).contact.equals(this.contact);
    }
}
