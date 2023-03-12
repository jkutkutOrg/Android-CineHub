```mermaid
erDiagram
    movie {
        id id
        str name
        str description
        img_id banner
        int price
    }

    room {
        id id
        str name
        int rows
        int cols
    }

    projection {
        id id
        id room
        id movie
        timedate time
    }

    movie ||--o{ projection : is_displayed
    projection }o--|| room : is_hosted

    seat_reservation {
        id projection
        int row
        int col
        id reservation
    }

    projection ||--o{ seat_reservation : has_seats

    reservation {
        id id
        id user_id
    }

    seat_reservation }|--|| reservation : is_reserved_in

    user {
        id email
        str name
    }

    reservation }o--|| user : is_made_by

    special_seat {
      id id
      id room
      int row
      int col
      char state
    }

    room ||--o{ special_seat : has

```

## Features:
- Login / Sign up:
  - Handled by Firebase Auth.
  - The email and the name are stored in the database.
- Database will use FireStore database for the data.
- Database will use Storage for the resources:
  - Img banner in Movie.
- A user can make a reservation for a movie projection:
  - Can book one or multiple seats.
  - Once done, it can not change or delete the reservation.
- In the database level, a seat in a projection can have 2 states:
  - Available: Any user can book it with a reservation.
  - Used: A user booked it with a reservation.
- If there is no admin control, all data in the following data sources is constant:
  - room
  - movie
  - projection
  - special_seat

## Notes:
- Row and col in seat should be related to the room dimensions.
  - It will be verified by our API.