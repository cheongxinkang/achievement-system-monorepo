function initForm(document) {
    const manageFieldsBtn = document.getElementById('manageFieldsBtn');
    const fieldsContainer = document.querySelector('.fields-container');
    const modal = document.getElementById('fieldManagerModal');
    const closeModalBtn = document.getElementById('closeModalBtn');
    const modalTableBody = document.getElementById('modalFieldsTableBody');
    const addRowBtn = document.getElementById('addRowBtn');
    const saveModalBtn = document.getElementById('saveModalBtn');
    
    let currentFields = [];

    function openModal() {
        currentFields = [];
        const items = fieldsContainer.querySelectorAll('.field-item');
        items.forEach((item, index) => {
            const typeInput = item.querySelector('input[name$=".type"]');
            currentFields.push({
                _id: index, // Track original index
                type: typeInput ? typeInput.value : 'TEXT'
            });
        });

        renderModalTable();
        modal.classList.add('active');
    }

    function closeModal() {
        modal.classList.remove('active');
    }

    function renderModalTable() {
        modalTableBody.innerHTML = '';
        currentFields.forEach((field, i) => {
            const tr = document.createElement('tr');
            tr.dataset.id = field._id;
            
            tr.innerHTML = `
                <td>
                    <input type="number" class="row-index" value="${i + 1}" min="1" max="${currentFields.length}" />
                </td>
                <td>
                    <select class="row-type">
                        <option value="TEXT" ${field.type === 'TEXT' ? 'selected' : ''}>TEXT</option>
                        <option value="NUMERICAL" ${field.type === 'NUMERICAL' ? 'selected' : ''}>NUMERICAL</option>
                    </select>
                </td>
                <td class="actions-cell">
                    <button type="button" class="btn-delete row-delete">
                        <svg style="width:16px;height:16px;" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <line x1="18" y1="6" x2="6" y2="18"></line>
                            <line x1="6" y1="6" x2="18" y2="18"></line>
                        </svg>
                    </button>
                </td>
            `;
            modalTableBody.appendChild(tr);
        });
        attachModalListeners();
    }

    function attachModalListeners() {
        const indexInputs = modalTableBody.querySelectorAll('.row-index');
        const deleteBtns = modalTableBody.querySelectorAll('.row-delete');
        const typeSelects = modalTableBody.querySelectorAll('.row-type');

        indexInputs.forEach(input => {
            input.addEventListener('change', handleIndexChange);
        });

        deleteBtns.forEach(btn => {
            btn.addEventListener('click', handleDeleteRow);
        });

        typeSelects.forEach(select => {
            select.addEventListener('change', handleTypeChange);
        });
    }

    function handleIndexChange(e) {
        const tr = e.target.closest('tr');
        const id = parseInt(tr.dataset.id, 10);
        let newIndex = parseInt(e.target.value, 10) - 1; // 0-based index
        
        if (isNaN(newIndex) || newIndex < 0) newIndex = 0;
        if (newIndex >= currentFields.length) newIndex = currentFields.length - 1;

        const currentIndex = currentFields.findIndex(f => f._id === id);
        if (currentIndex === newIndex) {
            e.target.value = newIndex + 1;
            return;
        }

        // Move element in the array
        const field = currentFields.splice(currentIndex, 1)[0];
        currentFields.splice(newIndex, 0, field);
        
        // Re-render table to reflect new order
        renderModalTable();
    }

    function handleTypeChange(e) {
        const tr = e.target.closest('tr');
        const id = parseInt(tr.dataset.id, 10);
        const field = currentFields.find(f => f._id === id);
        if (field) {
            field.type = e.target.value;
        }
    }

    function handleDeleteRow(e) {
        const tr = e.target.closest('tr');
        const id = parseInt(tr.dataset.id, 10);
        currentFields = currentFields.filter(f => f._id !== id);
        renderModalTable();
    }

    function handleAddRow() {
        currentFields.push({
            _id: -1, // Indicates a new field
            type: 'TEXT'
        });
        renderModalTable();
    }

    function handleSaveModal() {
        const payload = currentFields.map(field => ({
            id: field._id,
            type: field.type
        }));

        const originalBtnText = saveModalBtn.innerText;
        saveModalBtn.innerText = 'Saving...';
        saveModalBtn.disabled = true;

        fetch('/templates/update-fields', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            }
            throw new Error('Network response was not ok');
        })
        .then(html => {
            document.open();
            document.write(html);
            document.close();
        })
        .catch(error => {
            console.error('Error updating fields:', error);
            alert('Failed to update fields configuration.');
            saveModalBtn.innerText = originalBtnText;
            saveModalBtn.disabled = false;
        });
    }

    if (manageFieldsBtn) manageFieldsBtn.addEventListener('click', openModal);
    if (closeModalBtn) closeModalBtn.addEventListener('click', closeModal);
    if (addRowBtn) addRowBtn.addEventListener('click', handleAddRow);
    if (saveModalBtn) saveModalBtn.addEventListener('click', handleSaveModal);
}

// Export for CommonJS (Jest testing) or attach to DOMContentLoaded for browser
if (typeof module !== 'undefined' && module.exports) {
    module.exports = { initForm };
} else {
    document.addEventListener('DOMContentLoaded', function() {
        initForm(document);
    });
}
