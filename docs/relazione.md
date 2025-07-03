# Relazione Tecnica – Biblioteca Digitale

## Introduzione al Progetto

Il progetto "Biblioteca Digitale" consiste nella realizzazione di un'applicazione desktop in linguaggio Java per la gestione di una biblioteca virtuale.  
L'obiettivo principale è simulare il funzionamento di un sistema bibliotecario, permettendo a utenti e amministratori di interagire con un catalogo di libri in modo semplice e intuitivo.

L'applicazione è sviluppata seguendo l'architettura **MVC (Model-View-Controller)** e fa ampio uso di **design pattern classici (GoF)** per garantire modularità, estendibilità e riutilizzabilità del codice.

Tra le funzionalità principali:
- Registrazione e login degli utenti
- Visualizzazione e ricerca dei libri
- Prestito e restituzione dei libri
- Gestione dei contenuti da parte degli amministratori
- Filtri avanzati e statistiche sull’utilizzo

L’interfaccia grafica è stata realizzata utilizzando **JavaFX**, al fine di offrire una user experience più moderna rispetto alla CLI.  
I dati vengono salvati e caricati da file in formato **JSON**, permettendo una persistenza semplice e portabile.

Il progetto è stato sviluppato con particolare attenzione alla chiarezza del codice, alla documentazione e alla copertura tramite test unitari.

## Metodologia di Sviluppo

Lo sviluppo del progetto è stato suddiviso in **quattro fasi principali**, ispirate a una metodologia a cascata adattata alle tempistiche e alla struttura del lavoro di gruppo.  
Questa suddivisione ci ha permesso di mantenere ordine, tracciabilità e una distribuzione chiara dei compiti.

### 🟦 1. Concezione
Durante questa fase iniziale, il team ha analizzato il problema da affrontare e i requisiti forniti dal docente.  
Sono stati individuati gli attori principali (utenti e amministratori), le funzionalità fondamentali e le criticità legate all’architettura e alla gestione dello stato del sistema.

Abbiamo anche valutato le tecnologie da adottare (JavaFX per l’interfaccia grafica, JSON per la persistenza) e identificato la necessità di applicare almeno 7 design pattern classici GoF.

---

### 🟨 2. Definizione
In questa fase ci siamo concentrati sulla progettazione dell’architettura e sulla distribuzione dei compiti tra i membri del team.  
Sono stati redatti i primi **diagrammi UML semplificati**, definite le responsabilità dei singoli pacchetti (`model`, `controller`, `view`, ecc.), e scelti i design pattern più adatti a ogni componente.

È stata inoltre impostata una repository Git per il versionamento del progetto e la collaborazione continua.

---

### 🟧 3. Realizzazione
Il lavoro di sviluppo è stato suddiviso tra i membri del team secondo le competenze e le preferenze individuali. La comunicazione è avvenuta regolarmente tramite strumenti collaborativi, con aggiornamenti condivisi sulla repository GitHub.  
Le principali aree realizzative sono state:

- Modellazione delle entità (`Libro`, `Utente`, `Prestito`) e delle rispettive logiche
- Implementazione dell'interfaccia grafica tramite **JavaFX**
- Realizzazione dei controller e gestione del flusso applicativo
- Gestione della persistenza su file JSON
- Integrazione progressiva dei **design pattern** selezionati (Singleton, Observer, Command, ecc.)

#### Competenze assegnate:

| Parte                               | Chi la gestisce     | Dettagli                                                                      |
|-------------------------------------|----------------------|--------------------------------------------------------------------------------|
| Modulo libri                        | Andrea               | Include: `Book.java`, `BookManager`, `BookController`                         |
| Modulo utenti + auth                | Diego                | Include: `User.java`, `UserManager`, `UserController`                         |
| Modulo prestiti                     | Diego                | Include: `Loan.java`, `LoanManager`, `LoanController`                         |
| Persistenza JSON                    | Andrea               | Gestione salvataggio/caricamento su file JSON con classi `Json*Manager`       |
| Interfaccia utente - utenti         | Diego                | Interfaccia in JavaFX dedicata agli utenti normali (registrazione, ricerca, prestiti) |
| Interfaccia utente - amministratori | Andrea               | JavaFX per funzioni admin (gestione libri, utenti, statistiche)               |
| Test e validazioni                  | Diego + Andrea       | Unit test per modelli, controller e persistenza (JUnit)                       |
| Integrazione e testing finale       | Diego + Andrea       | Demo completa, collaudo, debugging finale e rifinitura dell’UX                |
| Documentazione e diagrammi          | Diego + Andrea       | Relazione tecnica, diagrammi UML, README.md con istruzioni d'uso              |

---

### 🟥 4. Chiusura
Nella fase finale è stata effettuata la **verifica funzionale** del sistema e la stesura della **documentazione tecnica**.  
Sono stati creati test unitari, diagrammi UML definitivi, demo illustrative e il file `README.md` con le istruzioni per l'uso.  
Particolare attenzione è stata data alla separazione tra interfaccia e logica, alla qualità del codice e alla preparazione dei deliverables finali.

---

### 📊 Diagramma di Gantt

Per rappresentare visivamente l'avanzamento del lavoro e la suddivisione temporale delle fasi, è stato realizzato il seguente diagramma di Gantt:

![Diagramma Gantt](img/gantt.png)

> *Nota: il diagramma segue la pianificazione su 4 settimane suggerita dal docente.*

## Architettura Generale

Il progetto è strutturato seguendo il paradigma **MVC (Model–View–Controller)**, supportato da un’organizzazione modulare dei package. Ogni componente ha responsabilità ben definite e segue principi di riusabilità e manutenibilità.

### 📁 Struttura dei pacchetti

La struttura dei pacchetti del progetto è la seguente:(da ultimare)
```
Digital-Library/
├── database/
├── docs/
├── src/
│ ├── common/
│ │     ├── adaper/
│ │     ├── enums/
│ │     ├── interfaces/
│ │     ├── nullObject/
│ │     ├── observer/
│ │     ├── state/
│ │     └── strategy/
│ ├── controller/ 
│ ├── manager/ 
│ ├── model/ 
│ ├── persistence/ 
│ ├── style/ 
│ ├── utils/ 
│ ├── view/
│ │     ├── admin/
│ │     └── user/
│ └── MainApp.java
├── test/ 
│ ├── common/ 
│ │     ├── adaper/
│ │     ├── nullObject/
│ │     ├── state/
│ │     └── strategy/
│ ├── controller/ 
│ ├── manager/
│ ├── model/
│ ├── persistence/
│ └── utils/ 
├── README.md 
└── .gitignore 
```

---

### Flusso dell'applicazione
```
Utente (via interfaccia JavaFX)
↓
Vista (View - JavaFX)
↓
Controller (Coordina l’azione)
↓
├── Manager (Logica applicativa)
└── Persistenza (Salvataggio/caricamento su JSON)
```

---

### Esempio: Prestito di un libro

1. L’utente clicca su “Prendi in prestito” dalla GUI JavaFX.
2. La vista (`BookDetailView`, ad esempio) invoca il metodo corrispondente nel `LoanController`.
3. Il `LoanController`:
    - chiama il `LoanManager` per verificare la disponibilità del libro e creare il nuovo prestito;
    - aggiorna lo stato del libro tramite il `BookManager`;
    - richiama le classi di persistenza (`JsonLoanManager`, `JsonBookManager`) per **salvare** i cambiamenti nei file `loans.json` e `books.json`.

4. La vista viene aggiornata per riflettere il nuovo stato del libro (non disponibile) e per mostrare il prestito tra quelli attivi dell’utente.

---

### Diagramma UML della struttura del progetto

![Diagramma UML](img/uml.png)

---

## Design Pattern Utilizzati

Durante lo sviluppo del progetto sono stati utilizzati sette design pattern classici appartenenti al catalogo GoF. La scelta dei pattern non è stata casuale, ma guidata dalla volontà di rendere il codice più modulare, estensibile e aderente ai principi SOLID.

---

### 1. Singleton

![Singleton](img/pattern/singleton.png)

In un sistema multi-componente, è spesso necessario avere **un unico punto di accesso** a determinati oggetti condivisi (come i controller o i manager). Per evitare istanziazioni multiple e garantire l’accesso globale a un'unica istanza, è stato applicato il pattern **Singleton**.

Questo pattern è stato utilizzato nelle classi:

- `BookManager`, `LoanManager`, `UserManager`: ognuna di queste classi gestisce l’intero insieme di oggetti di dominio (libri, prestiti, utenti). Devono esistere come uniche istanze condivise all’interno dell’applicazione.
- `UserController`, `BookController`, `LoanController`: contengono la logica di collegamento tra GUI e manager. Sono centralizzati e devono essere univoci.
- `SessionManager`: gestisce la sessione utente corrente, e pertanto deve esistere una sola istanza visibile a tutta l’app.

Tutte queste classi implementano la tipica logica `getInstance()` e costruttore privato.

---

### 2. Null Object

📷 ![NullObject](pattern/nullobject.png)

Nel sistema era necessario **gestire i casi in cui una ricerca non produce risultati**, ad esempio:

- Un utente cerca un libro inesistente
- Un ID utente non corrisponde a nessun account

Invece di tornare `null` (con il rischio di `NullPointerException`), viene restituito un oggetto **"neutro"** che implementa un comportamento di default.

Sono state create le seguenti classi:

- `NullBook`: restituito quando un libro non è presente nei risultati.
- `NullUser`: per ricerche utente fallite.
- `NullLoan`: per prestiti non trovati.

Ognuna implementa un'interfaccia comune con i rispettivi oggetti reali (`Book`, `User`, `Loan`), ma si comporta in modo prevedibile e sicuro.

Questo pattern ha migliorato la robustezza del sistema e semplificato il codice nelle GUI e nei controller, evitando controlli espliciti su `null`.

---

### 3. Template Method

![Template](img/pattern/template.png)

Nel progetto è stato applicato il pattern **Template Method** per definire una struttura generica e riutilizzabile nella gestione della **persistenza dei dati su file JSON**.  
Il punto centrale dell’implementazione è rappresentato dalla classe astratta:

> `JsonTemplateManager<T>`

Questa classe fornisce un **modello standardizzato** per tutte le operazioni comuni di I/O:

- **Serializzazione** e **deserializzazione** tramite Gson
- Lettura da file (`load`)
- Scrittura su file (`save`)
- Versioni asincrone (`loadAsync`, `saveAsync`) tramite `FxTaskRunner`

La classe viene parametrizzata con il tipo `T` e con il tipo array `T[]`, in modo da gestire in modo generico **liste di oggetti di qualsiasi tipo** (libri, utenti, prestiti...).

---

#### Classi concrete

Le classi che estendono `JsonTemplateManager<T>` sono:

- `JsonBookManager` → gestisce `List<Book>`
- `JsonLoanManager` → gestisce `List<Loan>`
- `JsonUserManager` → gestisce `List<User>`

Ognuna specifica solo:
- il **file path**
- il tipo array (`Book[].class`, `Loan[].class`, ...)

Il resto (logica di lettura/scrittura) è ereditato automaticamente.

In questo modo, la logica di persistenza rimane modulare, coerente e facilmente estendibile a nuovi tipi di dati.

---

### 4. State

![State](img/pattern/state.png)

Nel progetto, il pattern **State** è stato adottato per modellare il comportamento variabile di un **prestito** (`Loan`) in base al suo stato attuale: **in corso**, **scaduto**, oppure **restituito**.  
Senza questo pattern, la gestione delle regole sarebbe ricaduta in una serie di controlli `if` o `switch` sparsi nella classe `Loan`, rendendo il codice poco manutenibile.

Il punto centrale dell’implementazione è rappresentato dall’interfaccia:

> `LoanState`

Questa interfaccia fornisce un **modello standardizzato** per rappresentare il comportamento di un prestito nei suoi diversi stati, attraverso i seguenti metodi:
- `isReturned()` – restituisce `true` se il prestito è stato restituito
- `isExpired(LocalDate expirationDate)` – verifica se il prestito è scaduto
- `isInProgress()` – indica se il prestito è attualmente attivo
- `getName()` – restituisce il nome leggibile dello stato

La classe `Loan` delega a `LoanState` la logica relativa allo stato del prestito, rendendo il comportamento interno flessibile e facilmente estendibile.

---

#### Classi concrete

Le classi che implementano `LoanState` sono:

- `InProgressState` → rappresenta un prestito attivo.
    - `isInProgress()` restituisce `true`,
    - gli altri metodi restituiscono `false`.

- `ExpiredState` → rappresenta un prestito scaduto.
    - `isExpired(...)` restituisce `true`.
    - gli altri metodi restituiscono `false`.

- `ReturnedState` → rappresenta un prestito già restituito.
    - `isReturned()` restituisce `true`,
    - gli altri metodi restituiscono `false`.

Ognuno di questi stati implementa in modo diverso i metodi della super-interfaccia, permettendo al contesto (`Loan`) di comportarsi in maniera coerente e priva di `if`.

In questo modo, la logica di gestione degli stati rimane **pulita, chiara** e facilmente **estendibile** a nuovi scenari (es. prenotazione, sospensione, ecc.).

---

### 5. Strategy

![Strategy](img/pattern/strategy.png)

---

### 6. Adapter

![Adapter](img/pattern/adapter.png)

Nel progetto è stato applicato il pattern **Adapter** per garantire la compatibilità tra le **interfacce e classi di dominio** usate nel progetto e il sistema di **serializzazione JSON** basato su `Gson`.  
Il punto centrale dell’implementazione è rappresentato dalle classi:

> `InterfaceAdapter<T>`  
> `LocalDateAdapter`

Entrambi gli adapter **implementano le interfacce `JsonSerializer<T>` e `JsonDeserializer<T>`** offerte da Gson, che permettono di definire manualmente **come serializzare e deserializzare un tipo specifico**.

---

#### Obiettivi degli adapter

- **`InterfaceAdapter<T>`**  
  Gson non può serializzare o deserializzare correttamente oggetti rappresentati da interfacce (come `IBook`, `IUser`, `ILoan`).  
  Questo adapter permette di specificare una **classe concreta** da utilizzare durante la serializzazione e la ricostruzione dell'oggetto.  
  Gestisce inoltre l’inserimento esplicito del tipo (`@type`) se necessario.

- **`LocalDateAdapter`**  
  Il tipo `LocalDate` non è supportato nativamente da Gson.  
  Questo adapter converte il valore in una **stringa ISO** al momento della serializzazione, e viceversa in fase di lettura.

Entrambi vengono registrati nel builder `Gson` nella classe `JsonTemplateManager<T>`:

```java
Gson gson = new GsonBuilder()
    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
    .registerTypeAdapter(IBook.class, new InterfaceAdapter<>(Book.class))
    .registerTypeAdapter(IUser.class, new InterfaceAdapter<>(User.class))
    .registerTypeAdapter(ILoan.class, new InterfaceAdapter<>(Loan.class))
    .create();
```

---

### 7. Observer

![Observer](img/pattern/observer.png)

Nel progetto è stato applicato il pattern **Observer** per gestire in modo disaccoppiato la **comunicazione tra componenti della GUI**.  
Il punto centrale dell’implementazione è rappresentato dalle interfacce:

> `ViewSubject`  
> `ViewObserver`

Queste due interfacce definiscono un **meccanismo di registrazione e notifica** tra soggetti e osservatori all'interno dell'interfaccia utente realizzata con JavaFX.

---

#### 🧩 Obiettivo dell'Observer

All’interno dell’applicazione, alcune componenti della vista devono reagire automaticamente a **cambiamenti di stato**, come ad esempio:
- il login di un utente
- il cambio di schermata
- la modifica di un contenuto nella dashboard

Invece di aggiornare manualmente ogni componente, si è implementato un sistema **reactive-style**: un oggetto soggetto (`ViewSubject`) notifica automaticamente tutti gli osservatori (`ViewObserver`) registrati quando cambia qualcosa.

---

#### 📦 Interfacce definite

- **`ViewObserver`**  
  Definisce il metodo:

  ```java
  void updateView();
  ```
  che viene chiamato ogni volta che il soggetto cambia.


- **`ViewSubject`**  
  Permette di registrare, rimuovere e notificare gli osservatori:
  ```java
  void setObserver(ViewObserver observer);
  ```
  Ogni componente che implementa ViewSubject può quindi inviare notifiche.

---

## 🧪 7. Testing

Il sistema è stato testato mediante una combinazione di **test unitari** e **test di integrazione**, con l'obiettivo di verificare il corretto funzionamento dei componenti principali e la stabilità delle interazioni tra moduli.  
Il framework utilizzato per l’esecuzione dei test è:

> `JUnit 5`

---

### ✅ Strategie adottate

- **Test unitari**: applicati a singole classi, con particolare attenzione a:
  - correttezza delle operazioni logiche (es. `LoanManager`)
  - gestione dei dati (es. `JsonBookManager`)
  - metodi di utilità e filtri (`BookQueryUtils`)

- **Test di integrazione**: verificano il comportamento combinato di più componenti, ad esempio:
  - `BookController` ↔ `BookManager` + `JsonBookManager`
  - `LoanController` ↔ `LoanManager` + `BookManager`

L’approccio seguito è stato *build-and-test*: ogni modulo è stato testato isolatamente subito dopo la sua implementazione, per facilitare il debug e garantire coerenza fin dalle prime fasi di sviluppo.

---

### 🧪 Classi testate

#### **Unit test**
| Package             | Classi testate                         |
|---------------------|----------------------------------------|
| `common.adapter`    | `InterfaceAdapterTest`, `LocalDateAdapterTest` |
| `model`             | `BookTest`, `LoanTest`                 |
| `manager`           | `BookManagerTest`, `LoanManagerTest`   |
| `persistence`       | `JsonTemplateManagerTest`              |
| `common.nullObject` | `NullBookTest`, `NullLoanTest`         |
| `common.state`      | `ExpiredStateTest`                     |
| `common.strategy`   | `AuthorSortStrategyTest`, `BookSortStrategyFactoryTest` |
| `utils`             | `BookQueryUtilsTest`, `FxTaskRunnerTest` |

#### **Integration test**
| Package       | Classi testate                       |
|---------------|--------------------------------------|
| `controller`  | `BookControllerTest`, `LoanControllerTest` |

---

### 🗂️ Struttura della cartella `test/`
```
Digital-Library/
├── test/
│ ├── common/
│ │ ├── adapter/
│ │ ├── nullObject/
│ │ ├── state/
│ │ └── strategy/
│ ├── controller/
│ ├── manager/
│ ├── model/
│ ├── persistence/
│ └── utils/
```

---