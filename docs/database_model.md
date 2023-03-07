```mermaid
erDiagram
    Movie {
        id id
        str name
        str description
        img_id banner
        int price
    }

    Room {
        id id
        str name
    }

    Projection {
        id id
        id room
        id movie
        timedate time
    }

    Movie ||--o{ Projection : is_displayed
    Projection }o--|| Room : is_hosted


```