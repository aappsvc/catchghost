package com.rickqin.catchghost.takephoto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 *
 * @author Rick Qin <rickqinj@gmail.com>
 */
@Entity
@Table(name = "photo")
public class PhotoEntity {
    
    @Id
    @SequenceGenerator(name = "photo_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_seq")
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;
    
    @NotEmpty
    @Column(name = "workorder", length = 100, nullable = false)
    private String workorder;
    
    @NotNull
    @Column(name = "created", nullable = false)
    private LocalDate created;
    
    @NotEmpty
    @Column(name = "category_folder", length = 6, nullable = false)
    private String categoryFolder;
    
    @NotEmpty
    @Column(name = "filename", length = 100, nullable = false)
    private String filename;
    
    @Column(name = "notes", length = 255, nullable = true)
    private String notes;

    public String getCategoryFolder() {
        return categoryFolder;
    }

    public void setCategoryFolder(String categoryFolder) {
        this.categoryFolder = categoryFolder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkorder() {
        return workorder;
    }

    public void setWorkorder(String workorder) {
        this.workorder = workorder;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    
}
