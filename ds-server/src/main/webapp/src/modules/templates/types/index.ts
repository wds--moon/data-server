export interface BreadcrumbItem {
  to?: string,
  title: string
}

export interface TemplateState {
  breadcrumbItems: BreadcrumbItem[] | string[]
}
