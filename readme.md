Die Abgaben sind in den jeweiligen Branches.

# Aufgabe 1

## Fragen zum Start

### Welche Aufgabe haben Ports in einem Komponentendiagramm?

In einem Komponentendiagramm definieren Ports die Kommunikationsschnittstellen zwischen den Komponenten des Diagramms.

Diese Ports verbinden die Komponente mit ihrer Umgebung und schirmen sie gleichzeitig ab.
Sie ermöglichen es, mit externen Entitäten über klar spezifizierte Schnittstellen zu kommunizieren.
Durch diese Trennung wird die interne Struktur der Komponente von der äußeren Welt isoliert.
Dies fördert einen modularen Aufbau und flexible Kommunikation.

Darüber hinaus können Ports zusätzliche Funktionen übernehmen, etwa das Filtern von Daten, das Caching von Informationen oder die Überwachung von Protokollen.


### Wie könnte man deren Aufgabe für eine Komponente in Java implementieren?

Ein Port-Interface erstellen: Festlegung, wie die Schnittstellenmethoden für die Kommunikation aussehen sollen

&nbsp;&nbsp; Bsp. Praxis: Externe Systeme nutzen die Schnittstelle "ProductManagementInt", um mit der ProductManagement-Komponente zu kommunizieren. Da diese Aufgabe von dem Port übernommen werden soll, muss dieser die Schnittstelle "ProductManagementInt" für die ProductManagement-Komponente implementieren und externen Komponenten zur Verfügung stellen. 

Komponente: Die Komponente entspricht dem in Java implementierten Package. Sie enthält die Logik und Struktur der Anwendung in Form der Klassen ProductController und ProductRepository.

&nbsp;&nbsp; Bsp. Praxis: Die Klasse ProductController: Steuerlogik, ProductRepository: Datenverwaltung

Komposition: Der ProductController besitzt zwei Ports, über die er mit externen Komponenten kommuniziert.

&nbsp;&nbsp; Bsp. Praxis: Die Ports sind Teil der ProductManagement Komponente (durch Boxen dargestellt).

Delegation: Weitergabe von Aufgaben an einen externen Service.

&nbsp;&nbsp; Bsp. Praxis: Der ProductController nutzt intern ProductRepository und greift extern über den Caching-Port auf Caching-Funktionalität zu.

### Wie können benötigte bzw. angebotene Interfaces injiziert bzw. entnommen werden?

Angebotene Interfaces werden in Java als öffentliche Interfaces oder Klassen bereitgestellt. Andere Komponenten können darauf zugreifen, beispielsweise über Methodenaufrufe.

&nbsp;&nbsp; Bsp. Praxis: getProductByName( name : String ) : Product[]

Benötigte Interfaces Werden über Dependency Injection (z. B. mit @Autowired in Spring) in eine Klasse eingefügt. Die Komponente bekommt somit die Funktionalität von außen geliefert.

&nbsp;&nbsp; Bsp. Praxis: cacheResult( key : String, value : List<Object> )


## Implementierungsfragen FA0

### Welches Design Pattern sollte hier verwendet werden, um die notwendige Delegation zwischen internen und externen Verhalten zu realisieren?

Delegation-Pattern: Die Aufrufe der Interfaces werden an die Ports weitergereicht.

## Implementierungsfragen FA1

### Muss das Interface ProductManagementInt ggf. noch um weitere Methoden erweitert werden?

Es ist sinnvoll das Interface um die üblichen CRUD Operationen zu erweitern.

- Vorhandene Operationen
  - Read (Lesende Operation)
    - getProductByName(String name)
- Mögliche Erweiterungen
  - Create (Erstellende Operation)
    - addProduct(Product product);
  - Read (Lesende Operation)
    - getProductById(Int id)
  - Update (Aktualisierende Operation)
    - updateProduct(Product product)
  - Delete (Löschende Operation)
    - deleteProduct(int productId)
   
### Gibt es eine dedizierte Reihenfolge beim Aufruf der Methoden des Interfaces?

Ja es gibt eine dedizierte Reihenfolge beim Aufrufen der Methoden des Interfaces wie in der Spezifikation beschrieben. Zunächst muss eine Session mittels openConnection() eröffnet werden. Daraufhin können mittels der oben beschriebenen CRUD-Operationen die Daten ausgegeben und manipuliert werden. ***Das Ausgeben und Manipulieren der Daten kann aus Sicht des Interfaces in beliebiger Reihenfolge passieren.*** Abschließend muss mit closeConnection() die Verbindung wieder geschlossen werden.
