import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TestSkill } from './test-skill.model';
import { TestSkillService } from './test-skill.service';

@Injectable()
export class TestSkillPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private testSkillService: TestSkillService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.testSkillService.find(id).subscribe((testSkill) => {
                this.testSkillModalRef(component, testSkill);
            });
        } else {
            return this.testSkillModalRef(component, new TestSkill());
        }
    }

    testSkillModalRef(component: Component, testSkill: TestSkill): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.testSkill = testSkill;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
