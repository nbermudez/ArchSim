/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Edit;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudezs@gmail.com >
 */
public interface Editable {
    EditInfo getEditInfo(int n);
    void setEditValue(int n, EditInfo ei);
}