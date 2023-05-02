package library.libraryManager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")
public class User{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false,length=32)
	private String username;
	@Column(nullable=false,unique=true,length=128)
	private String email;
	@Column(nullable=false,length=64)
	private String name;
	@Column(nullable=false,length=128)
	private String password;
	private boolean active=true;


	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
	@JoinTable(
			name="user_role",
			joinColumns=@JoinColumn(name="USER_ID", referencedColumnName = "ID"),
			inverseJoinColumns =@JoinColumn(name="ROLE_ID",referencedColumnName = "ID")
			  )

	private List<Role> roles=new ArrayList<>();


}