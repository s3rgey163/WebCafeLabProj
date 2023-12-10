package ru.ssau.webcaffe.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = Category.TABLE_NAME)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Category {
    public static final String TABLE_NAME = "product_category";
    public static final String REFERENCE_PK_NAME = TABLE_NAME + "_id";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(unique = true)
    private String name;

    private String describe;


    @OneToMany(
            mappedBy = "category",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @ToString.Exclude
    private Set<Product> products;

    public Category() {}

    public Category(String name) {
        this(0, name);
    }

    public Category(String name, String describe) {
        this(0, name, describe, null);
    }

    public Category(long id, String name) {
        this(0, name, null, null);
    }

    public Category(String name, String describe, Set<Product> products) {
        this(0, name, describe, products);
    }

    public Category(
            long id,
            String name,
            String describe,
            Set<Product> products
    ) {
        this.id = id;
        this.name = name;
        this.describe = describe;
        this.products = products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
        this.products.forEach(p ->  p.setCategory(this));
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Category category = (Category) o;
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
